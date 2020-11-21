package com.ongdev.blog.api.controllers

import com.ongdev.blog.api.models.dtos.requests.ArticleCreationRequest
import com.ongdev.blog.api.models.dtos.requests.ArticleUpdatingRequest
import com.ongdev.blog.api.models.dtos.responses.ArticleCreationResponse
import com.ongdev.blog.api.models.dtos.responses.ArticleListWithPaginationResponse
import com.ongdev.blog.api.models.dtos.responses.ArticleUpdatingResponse
import com.ongdev.blog.api.services.interfaces.ArticleService
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("articles")
class ArticleController(private val articleService: ArticleService) {

    @PostMapping
    fun createArticle(@RequestBody articleCreationRequest: ArticleCreationRequest): ResponseEntity<ArticleCreationResponse> {
        val articleCreationResponse = articleService.createArticle(articleCreationRequest)
        return ResponseEntity(articleCreationResponse, HttpStatus.OK)
    }

    @GetMapping
    fun getAllArticles(@PageableDefault(size = 10)
                       @RequestParam(name = "page", defaultValue = "0") page: Int
    ): ResponseEntity<ArticleListWithPaginationResponse> {
        val articleListResponse = articleService.getArticlesWithPagination(page)
        return ResponseEntity(articleListResponse, HttpStatus.OK)
    }

    @PutMapping
    fun updateArticle(
            @RequestParam(name = "id", required = true) id: String,
            @RequestBody articleUpdatingRequest: ArticleUpdatingRequest
    ): ResponseEntity<ArticleUpdatingResponse> = ResponseEntity(
            articleService.updateArticle(articleUpdatingRequest, id),
            HttpStatus.OK)

    @DeleteMapping("{id}")
    fun deleteArticle(@PathVariable(name = "id", required = true) id: String): ResponseEntity<Void> {
        articleService.deleteArticle(id)
        return ResponseEntity(HttpStatus.OK)
    }

    @GetMapping("{id}")
    fun getArticle(@PathVariable(name = "id", required = true) id: String): ResponseEntity<ArticleCreationResponse> {
        return ResponseEntity(articleService.getArticleById(id), HttpStatus.OK)
    }
}