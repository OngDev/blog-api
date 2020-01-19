package com.ongdev.blog.api.controllers

import com.ongdev.blog.api.models.dtos.requests.ArticleCreationRequest
import com.ongdev.blog.api.models.dtos.responses.ArticleCreationResponse
import com.ongdev.blog.api.services.interfaces.ArticleService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/articles")
class ArticleController @Autowired constructor(private val articleService : ArticleService) {

    @PostMapping
    fun createArticle(@RequestBody articleCreationRequest: ArticleCreationRequest) : ResponseEntity<ArticleCreationResponse> {
        val articleCreationResponse = articleService.createArticle(articleCreationRequest)
        return ResponseEntity(articleCreationResponse, HttpStatus.OK)
    }
}