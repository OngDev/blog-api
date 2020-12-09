package com.ongdev.blog.api

import com.ongdev.blog.api.exceptions.*
import com.ongdev.blog.api.models.dtos.requests.ArticleCreationRequest
import com.ongdev.blog.api.models.dtos.requests.ArticleUpdatingRequest
import com.ongdev.blog.api.models.entities.Article
import com.ongdev.blog.api.models.repositories.ArticleRepository
import com.ongdev.blog.api.models.toArticleEntity
import com.ongdev.blog.api.services.impl.ArticleServiceImpl
import com.ongdev.blog.api.services.interfaces.ArticleService
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import java.util.*
import kotlin.collections.ArrayList

class ArticleServiceTests {

    private val articleRepository: ArticleRepository = Mockito.mock(ArticleRepository::class.java)

    private var articleService: ArticleService = ArticleServiceImpl(articleRepository)

    private lateinit var mockArticleCreationRequest: ArticleCreationRequest
    private lateinit var mockArticleUpdatingRequest: ArticleUpdatingRequest
    private lateinit var mockArticle: Article
    private lateinit var mockOptionalArticle: Optional<Article>
    private lateinit var mockPageArticles: Optional<Page<Article>>

    @BeforeEach
    internal fun setUp() {
        mockArticleCreationRequest = ArticleCreationRequest(
                "Test title",
                "Test description",
                "Test content",
                "Test link"
        )
        mockArticleUpdatingRequest = ArticleUpdatingRequest(
                "Test update title",
                "Test update description",
                "Test update content",
                "Test update link"
        )
        mockArticle = mockArticleCreationRequest.toArticleEntity()
        mockArticle.id = UUID.randomUUID()
        mockOptionalArticle = Optional.of(mockArticle)
        val totalElement = ArrayList<Article>()
        for (i in 1..10) {
            totalElement.add(mockArticle)
        }
        val pagingElement: PageImpl<Article> = PageImpl(totalElement)
        mockPageArticles = Optional.of(pagingElement)
    }

    @Test
    fun `Get Article, should return a Article by id`() {
        Mockito.`when`(articleRepository.findById(Mockito.any(UUID::class.java))).thenReturn(mockOptionalArticle)

        val result = articleService.getArticleById(UUID.randomUUID().toString())

        Assertions.assertThat(result.id).isEqualTo(mockArticle.id.toString())
    }

    @Test
    fun `Get Article, should throw error when failed to find entity`() {
        val uuid = Mockito.any(UUID::class.java)
        Mockito.`when`(articleRepository.findById(uuid)).thenThrow(EntityNotFoundException::class.java)

        assertThrows<EntityNotFoundException> { articleService.getArticleById(UUID.randomUUID().toString()) }
    }

    @Test
    fun `Create Article, should return Article`() {
        Mockito.`when`(articleRepository.save(Mockito.any(Article::class.java)))
                .thenReturn(mockArticle)

        val result = articleService.createArticle(mockArticleCreationRequest)

        Assertions.assertThat(result.id).isEqualTo(mockArticle.id.toString())
    }

    @Test
    fun `Create Article, should throw error when failed to save entity`() {
        Mockito.`when`(articleRepository.save(Mockito.any(Article::class.java))).thenThrow(IllegalArgumentException())

        assertThrows<EntityCreationFailedException> { articleService.createArticle(mockArticleCreationRequest) }
    }

    @Test
    fun `Create Article, should throw error when link is existed`() {
        Mockito.`when`(articleRepository.findByLink("Test link")).thenThrow(EntityIsExistedException::class.java)

        assertThrows<EntityIsExistedException> { articleService.createArticle(mockArticleCreationRequest) }
    }

    @Test
    fun `Update Article, should return updated Article`() {
        Mockito.`when`(articleRepository.findById(Mockito.any(UUID::class.java))).thenReturn(mockOptionalArticle)
        Mockito.`when`(articleRepository.save(Mockito.any(Article::class.java))).thenReturn(mockArticle)

        val result = articleService.updateArticle(mockArticleUpdatingRequest, UUID.randomUUID().toString())

        Assertions.assertThat(result.id).isEqualTo(mockArticle.id.toString())
    }

    @Test
    fun `Update Article, should throw error when failed to find entity`() {
        Mockito.`when`(articleRepository.findById(Mockito.any(UUID::class.java))).thenThrow(EntityNotFoundException::class.java)

        assertThrows<EntityNotFoundException> { articleService.updateArticle(mockArticleUpdatingRequest, UUID.randomUUID().toString()) }
    }

    @Test
    fun `Update Article, should throw error when failed to save entity`() {
        Mockito.`when`(articleRepository.findById(Mockito.any(UUID::class.java))).thenReturn(mockOptionalArticle)
        Mockito.`when`(articleRepository.save(Mockito.any(Article::class.java))).thenThrow(IllegalArgumentException())

        assertThrows<EntityUpdatingFailedException> { articleService.updateArticle(mockArticleUpdatingRequest, UUID.randomUUID().toString()) }
    }


    @Test
    fun `Update Article, should throw error when link is existed`() {
        Mockito.`when`(articleRepository.findByLink("Test update link")).thenThrow(EntityIsExistedException::class.java)

        assertThrows<EntityIsExistedException> { articleService.updateArticle(mockArticleUpdatingRequest, UUID.randomUUID().toString()) }
    }

    @Test
    fun `Delete Article, should throw error when failed to find entity`() {
        Mockito.`when`(articleRepository.findById(Mockito.any(UUID::class.java))).thenThrow(EntityNotFoundException::class.java)

        assertThrows<EntityNotFoundException> { articleService.deleteArticle(UUID.randomUUID().toString()) }
    }

    @Test
    fun `Delete Article, should throw error when failed to delete entity`() {
        Mockito.`when`(articleRepository.findById(Mockito.any(UUID::class.java))).thenReturn(mockOptionalArticle)
        Mockito.`when`(articleRepository.delete(Mockito.any(Article::class.java))).thenThrow(IllegalArgumentException())

        assertThrows<EntityDeletingFailedException> { articleService.deleteArticle(UUID.randomUUID().toString()) }
    }

    @Test
    fun `Get Articles without title, should return Articles`() {
        val page = PageRequest.of(0, 10)
        Mockito.`when`(articleRepository.findAll(page))
                .thenReturn(mockPageArticles.get())
        val result = articleService.getArticlesWithPaginationAndSort(page)

        Assertions.assertThat(result.result.totalElements).isEqualTo(10)
    }

    @Test
    fun `Get Articles with title, should return Articles`() {
        val page = PageRequest.of(0, 10)
        Mockito.`when`(articleRepository.findAllByTitle("Test title", page))
                .thenReturn(mockPageArticles.get())
        val result = articleService.getArticlesByTitleWithPaginationAndSort("Test title", page)

        Assertions.assertThat(result.result.totalElements).isEqualTo(10)
    }
    @Test
    fun `Get Articles By Category, should return Articles`() {
        val uuid = UUID.randomUUID()
        val page = PageRequest.of(0, 10)
        Mockito.`when`(articleRepository.findAllArticlesByCategoryId(uuid, page))
                .thenReturn(mockPageArticles.get())
        val result = articleService.getArticlesByCategory(uuid.toString(), page)

        Assertions.assertThat(result.result.totalElements).isEqualTo(10)
    }
}