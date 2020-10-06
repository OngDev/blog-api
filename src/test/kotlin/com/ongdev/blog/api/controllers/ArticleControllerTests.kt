package com.ongdev.blog.api.controllers

import com.ongdev.blog.api.models.dtos.responses.ArticleListWithPaginationResponse
import com.ongdev.blog.api.models.entities.Article
import com.ongdev.blog.api.models.entities.Author
import com.ongdev.blog.api.models.entities.Category
import com.ongdev.blog.api.models.toArticleCreationResponse
import com.ongdev.blog.api.services.interfaces.ArticleService
import org.junit.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.util.*
import kotlin.collections.ArrayList


@WebMvcTest
class ArticleControllerTests(@Autowired val mockMvc: MockMvc) {

    @MockBean
    private lateinit var articleService: ArticleService

    @Test
    fun `test Get List Articles By Category, Successfully`() {
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

        mockMvc.perform(MockMvcRequestBuilders.get("/articles/category/$tempCategoryId").accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
    }
}