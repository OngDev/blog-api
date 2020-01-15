package com.ongdev.blog.api.controllers

import com.ongdev.blog.api.models.dtos.ArticleDTO
import com.ongdev.blog.api.models.toArticleDTO
import com.ongdev.blog.api.models.toArticleEntity
import com.ongdev.blog.api.services.interfaces.ArticleService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/posts")
class PostController @Autowired constructor(private val articleService : ArticleService) {

    @PostMapping
    fun createPost(@RequestBody articleDTO: ArticleDTO) : ArticleDTO {
        return articleService.createPost(articleDTO.toArticleEntity()).toArticleDTO()
    }
}