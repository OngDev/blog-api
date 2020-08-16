package com.ongdev.blog.api
import com.ongdev.blog.api.models.entities.Article
import com.ongdev.blog.api.models.repositories.ArticleRepository
import com.ongdev.blog.api.services.impl.ArticleServiceImpl
import com.ongdev.blog.api.services.interfaces.ArticleService
import org.assertj.core.api.Assert
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.skyscreamer.jsonassert.JSONAssert.assertEquals
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.util.*

//import org.springframework.test.util.AssertionErrors.assertNotNull


@ExtendWith(SpringExtension::class)
@ContextConfiguration
@TestPropertySource("/application-test.properties")
class TestAddServiceToGetAPostById {
    private val articleRepository: ArticleRepository = mock(ArticleRepository::class.java)
    private lateinit var articleService : ArticleService
//    @BeforeEach
//    fun setUp(){
//        val id = "1807540"
//        val id1="1807541"
//        val id2="1807542"
//        val optionalArticle=articleRepository.findById(id)
//        `when`(articleRepository.findById(Mockito.anyString())).thenReturn(id)
//        articleService = ArticleServiceImpl(articleRepository)
//    }
    @Test
    fun TestAddServiceToGetAPostById() {
//        val testArticle=articleService.addServiceToGetAPostById("1807540")
//        assertNotNull("With id 1",article)
//        val listId=mutableListOf<String>("1807540","1807541","1807541")
        val listArticle= listOf(
                ArticleRepository<Article("1","1","1","hi",null,"1"),UUID.fromString("1807541")>,
                ArticleRepository<Article("2","2","2","hi",null,"2"),UUID.fromString("1807542")>,
                ArticleRepository<Article("3","3","3","hi",null,"3"),UUID.fromString("1807543")>,
//
        )
        Mockito.`when`(articleRepository.findById(Mockito.anyString())).thenReturn(listArticle)
        articleService=ArticleServiceImpl(articleRepository)
        assertEquals("1807541",articleService.addServiceToGetAPostById("1807541"),)


    }

}