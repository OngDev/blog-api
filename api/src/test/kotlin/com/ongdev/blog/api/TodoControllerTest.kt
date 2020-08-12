package com.ongdev.blog.api

import com.ongdev.blog.api.models.entities.Article
import com.ongdev.blog.api.models.repositories.ArticleRepository
import com.ongdev.blog.api.services.impl.ArticleServiceImpl
import com.ongdev.blog.api.services.interfaces.ArticleService
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Bean
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers


@WebMvcTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension::class)
@ContextConfiguration(classes = [ArticleServiceImpl::class])
class TodoControllerTest {

    @TestConfiguration
    class hi{
        @Bean
        fun articleService()= Mockito.mock(ArticleServiceImpl::class.java)
    }

    @MockBean
    lateinit var articleService: ArticleService
    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    @Throws(Exception::class)
    fun testGetAListArticleWithTag() {
        val sample = listOf(
                Article("1", "1", "1", "hi", null, "1")
                , Article("2", "2", "2", "hi", null, "2")
                , Article("3", "3", "3", "hi", null, "3")
        )

        given(articleService.getAllStoriesWithTag("hi"))
                .willReturn(sample)

        mockMvc.perform(MockMvcRequestBuilders.get("/api/articles/hi")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk)
//                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize<Any>(3)))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title", CoreMatchers.`is`("1")))
    }
}
