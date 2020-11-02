package com.ongdev.blog.api.services

import com.ongdev.blog.api.exceptions.ListArticlesNotFoundException
import com.ongdev.blog.api.models.dtos.requests.ArticleCreationRequest
import com.ongdev.blog.api.models.entities.Article
import com.ongdev.blog.api.models.repositories.ArticleRepository
import com.ongdev.blog.api.models.toArticleEntity
import com.ongdev.blog.api.services.impl.ArticleServiceImpl
import com.ongdev.blog.api.services.interfaces.ArticleService
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.*
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import java.util.*

class ArticleServiceTests {
    private val articleRepository: ArticleRepository = mock(ArticleRepository::class.java)

    private var articleService: ArticleService = ArticleServiceImpl(articleRepository)

    private lateinit var mockArticleCreationRequest: ArticleCreationRequest
    private lateinit var mockArticle: Article
    private lateinit var mockOptionalArticle: Optional<Article>

    @BeforeEach
    internal fun setUp() {
        mockArticleCreationRequest = ArticleCreationRequest(
                "Test content",
                "Test description",
                "Test content",
                Date(),
                "Test link",
                setOf()
        )
        mockArticle = mockArticleCreationRequest.toArticleEntity()
        mockArticle.id = UUID.randomUUID()
        mockOptionalArticle = Optional.of(mockArticle)
    }

    @Test
    fun `Get articles, should return pagination list`() {
        val mockListArticle = PageImpl(listOf(mockArticle))
        `when`(
                articleRepository.findAllArticlesByCategoryId(any(UUID::class.java), any(Pageable::class.java))
        ).thenReturn(Optional.of(mockListArticle))

        val mockRequestTOPage = PageImpl(listOf(mockArticleCreationRequest))
        val resultPage = articleService.getListArticlesByCategory(UUID.randomUUID().toString(), PageRequest.of(0, 10))

        assertThat(resultPage.result.totalElements).isEqualTo(mockRequestTOPage.size)
    }

    @Test
    fun `Get articles, should throw error when failed to find articles`() {
        val randomUUID = UUID.randomUUID()
        val pageable = PageRequest.of(0, 10)
        `when`(articleRepository.findAllArticlesByCategoryId(randomUUID, pageable))
                .thenThrow(ListArticlesNotFoundException())

        assertThrows<ListArticlesNotFoundException> {
            articleService.getListArticlesByCategory(randomUUID.toString(), pageable)
        }
    }

}