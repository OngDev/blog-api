package com.ongdev.blog.api

import com.ongdev.blog.api.models.entities.Article
import com.ongdev.blog.api.services.interfaces.ArticleService
import org.hamcrest.CoreMatchers
import org.hamcrest.Matchers
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@ExtendWith(SpringExtension::class)
@WebMvcTest
class TodoControllerTest @Autowired constructor(private val mockMvc: MockMvc) {

    @MockBean
    lateinit var articleService: ArticleService

    @Test
    @Throws(Exception::class)
    fun testGetAListArticleWithTag() {
        val sample = listOf(
                Article("1", "1", "1", "hi", null, "1")
                , Article("2", "2", "2", "hi", null, "2")
                , Article("3", "3", "3", "hi", null, "3")
        )

        Mockito.`when`(articleService.getAllStoriesWithTag("hi"))
                .thenReturn(sample)

        mockMvc.perform(MockMvcRequestBuilders.get("/articles/{tag}","hi")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize<Any>(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title", CoreMatchers.`is`("1")))
    }
}
