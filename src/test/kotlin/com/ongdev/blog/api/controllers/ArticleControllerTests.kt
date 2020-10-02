package com.ongdev.blog.api.controllers

import com.ongdev.blog.api.models.dtos.responses.ArticleListWithPaginationResponse
import com.ongdev.blog.api.models.entities.Article
import com.ongdev.blog.api.models.entities.Author
import com.ongdev.blog.api.models.entities.Category
import com.ongdev.blog.api.models.toArticleCreationResponse
import com.ongdev.blog.api.services.interfaces.ArticleService
import junit.framework.Assert.assertEquals
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.util.*
import kotlin.collections.ArrayList

@ExtendWith(SpringExtension::class)
class ArticleControllerTests {

    private val articleService: ArticleService = Mockito.mock(ArticleService::class.java)

    private lateinit var articleController: ArticleController

    @Test
    fun testGetListArticlesForEachCategorySuccessfully() {
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
        val response = ArticleListWithPaginationResponse(pagingByCategory.map {
            it.toArticleCreationResponse()
        })
        Mockito.`when`(articleService
                .getListArticlesByCategory(tempCategoryId, pageable))
                .thenReturn(response)
        articleController = ArticleController(articleService)
        val result = articleController.getListArticlesForEachCategory(tempCategoryId,pageable)
        assertEquals(HttpStatus.OK, result.statusCode)
        assertEquals(response, result.body)
    }

    @Test
    fun testGetListArticlesForEachCategoryBadly() {
        articleController = ArticleController(articleService)
        val result = articleController.getListArticlesForEachCategory(UUID.randomUUID().toString(),PageRequest.of(0, 10))
        assertEquals(null, result.body)
    }
}