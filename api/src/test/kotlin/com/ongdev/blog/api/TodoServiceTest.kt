package com.ongdev.blog.api

import com.ongdev.blog.api.models.entities.Article
import com.ongdev.blog.api.models.repositories.ArticleRepository
import com.ongdev.blog.api.services.impl.ArticleServiceImpl
import com.ongdev.blog.api.services.interfaces.ArticleService
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.util.AssertionErrors.assertEquals


@TestPropertySource("/application-test.properties")
@ExtendWith(SpringExtension::class)
class TodoServiceTest {

    private val articleRepository: ArticleRepository = Mockito.mock(ArticleRepository::class.java)
    private lateinit var articleService: ArticleService

    @Test
    fun testGetAllStoriesWithTag() {
        val sample = listOf(
                Article("1", "1", "1", "hi", null, "1")
                , Article("2", "2", "2", "hi", null, "2")
                , Article("3", "3", "3", "hi", null, "3")
        )

        Mockito.`when`(articleRepository.findAllByName(Mockito.anyString())).thenReturn(sample)
        articleService = ArticleServiceImpl(articleRepository)

        assertEquals("Number of stories should be 3", 3, articleService.getAllStoriesWithTag("hi").size)
    }
}