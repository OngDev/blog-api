package com.ongdev.blog.api

import com.ongdev.blog.api.exceptions.EntityCreationFailedException
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
    fun `Get Articles By Category, should return Articles`() {
        val uuid = UUID.randomUUID()
        val page = PageRequest.of(0, 10)
        Mockito.`when`(articleRepository.findAllArticlesByCategoryId(uuid, page))
                .thenReturn(mockPageArticles.get())
        val result = articleService.getArticlesByCategory(uuid.toString(), page)

        Assertions.assertThat(result.result.totalElements).isEqualTo(10)
    }
}