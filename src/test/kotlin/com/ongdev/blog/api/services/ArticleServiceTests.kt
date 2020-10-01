package com.ongdev.blog.api.services

import com.ongdev.blog.api.exceptions.ListArticlesNotFoundException
import com.ongdev.blog.api.models.entities.Article
import com.ongdev.blog.api.models.entities.Author
import com.ongdev.blog.api.models.entities.Category
import com.ongdev.blog.api.models.repositories.ArticleRepository
import com.ongdev.blog.api.services.impl.ArticleServiceImpl
import com.ongdev.blog.api.services.interfaces.ArticleService
import junit.framework.Assert.assertEquals
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.util.*
import kotlin.collections.ArrayList


@ExtendWith(SpringExtension::class)
class ArticleServiceTests {
    private val articleRepository: ArticleRepository = Mockito.mock(ArticleRepository::class.java)

    private lateinit var articleService: ArticleService

    @Test
    fun testGetListArticlesByCategorySuccessfully() {
        val category = Category("", "", emptySet())
        category.id = UUID.randomUUID()
        val tempCategoryId = category.id.toString()
        val setCategory = setOf(category)
        val listArticle = ArrayList<Article>()
        for (i in 0..9) {
            listArticle.add(i, Article("", "", "", "", null
                    , Author(), emptySet(), setCategory, emptySet()))
        }
        val pageable: Pageable = PageRequest.of(0, 10)
        val pagingByCategory: Page<Article> = PageImpl<Article>(listArticle, pageable, 1)
        val toOptional = Optional.of(pagingByCategory)
        Mockito.`when`(articleRepository
                .findAllArticlesByCategoryId(UUID.fromString(tempCategoryId), pageable))
                .thenReturn(toOptional)
        articleService = ArticleServiceImpl(articleRepository)
        assertEquals("Number of stories should be 10", 10
                , articleService.getListArticlesByCategory(tempCategoryId, pageable).result.totalElements)
    }

    @Test
    fun testGetListArticlesByCategoryBadly() {
        val category = Category("", "", emptySet())
        category.id = UUID.randomUUID()
        val setCategory = setOf(category)
        val listArticle = ArrayList<Article>()
        for (i in 0..9) {
            listArticle.add(i, Article("", "", "", "", null
                    , Author(), emptySet(), setCategory, emptySet()))
        }
        val pageable: Pageable = PageRequest.of(0, 10)
        val pagingByCategory: Page<Article> = PageImpl<Article>(listArticle, pageable, 1)
        val toOptional = Optional.of(pagingByCategory)
        Mockito.`when`(articleRepository
                .findAllArticlesByCategoryId(UUID.fromString(category.id.toString()), pageable))
                .thenReturn(toOptional)
        articleService = ArticleServiceImpl(articleRepository)
        Assertions.assertThrows(ListArticlesNotFoundException::class.java) {
            articleService.getListArticlesByCategory(UUID.randomUUID().toString(), pageable)
        }
    }

}