package com.ongdev.blog.api


import com.ongdev.blog.api.models.entities.Article
import com.ongdev.blog.api.models.repositories.ArticleRepository
import com.ongdev.blog.api.services.impl.ArticleServiceImpl
import io.mockk.mockk


import org.junit.jupiter.api.Assertions

import org.junit.jupiter.api.BeforeEach

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration


import org.springframework.context.annotation.Bean
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestPropertySource

import org.springframework.test.context.junit.jupiter.SpringExtension


@ExtendWith(SpringExtension::class)
@ContextConfiguration
@TestPropertySource("/application-test.properties")
class TodoServiceTest{

    @TestConfiguration
    class TodoServiceTest2Configuration {
        @Bean
        fun articleServiceImpl() = mockk<ArticleServiceImpl>()
    }

    @Mock
    val articleRepository: ArticleRepository = mockk()
    @Autowired
    val articleServiceImpl: ArticleServiceImpl = mockk()

    @BeforeEach
    fun setUp() {
        val sample = listOf(
                Article("1","1","1","hi",null, "1")
                , Article("2","2","2","hi",null, "2")
                , Article("3","3","3","hi",null, "3")
        )

        Mockito.`when`(articleRepository.findAll()).thenReturn(sample)
    }

    @Test
    fun testGetAllModels() {
        Assertions.assertEquals(3, articleServiceImpl.getAllStoriesWithTag("hi").size)
    }
}