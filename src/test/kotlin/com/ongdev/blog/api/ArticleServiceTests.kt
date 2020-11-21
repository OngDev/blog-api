package com.ongdev.blog.api

import com.ongdev.blog.api.exceptions.ArticleNotFoundException
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
import java.util.*

class ArticleServiceTests {

    private val articleRepository: ArticleRepository = Mockito.mock(ArticleRepository::class.java)

    private var articleService: ArticleService = ArticleServiceImpl(articleRepository)

    private lateinit var mockArticleCreationRequest: ArticleCreationRequest
    private lateinit var mockArticleUpdatingRequest: ArticleUpdatingRequest
    private lateinit var mockArticle: Article
    private lateinit var mockOptionalArticle: Optional<Article>

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
    }

    @Test
    fun `Get Article, should return a Article by id`() {
        Mockito.`when`(articleRepository.findById(Mockito.any(UUID::class.java))).thenReturn(mockOptionalArticle)

        val result = articleService.getArticleById(UUID.randomUUID().toString())

        Assertions.assertThat(result.id).isEqualTo(mockArticle.id.toString())
    }

    @Test
    fun `Get Article, should throw error when failed to find entity`() {
        Mockito.`when`(articleRepository.findById(Mockito.any(UUID::class.java))).thenThrow(ArticleNotFoundException())

        assertThrows<ArticleNotFoundException> { articleService.getArticleById(UUID.randomUUID().toString()) }
    }
}