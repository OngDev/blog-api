package com.ongdev.blog.api.controllers

import com.ongdev.blog.api.models.dtos.requests.ArticleCreationRequest
import com.ongdev.blog.api.models.dtos.requests.ArticleUpdatingRequest
import com.ongdev.blog.api.models.dtos.responses.ArticleResponse
import com.ongdev.blog.api.models.dtos.responses.ArticlesWithPaginationResponse
import com.ongdev.blog.api.services.interfaces.ArticleService
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("articles")
class ArticleController(private val articleService: ArticleService) {

    @PostMapping
    fun createArticle(@RequestBody articleCreationRequest: ArticleCreationRequest): ResponseEntity<ArticleResponse> {
        val articleCreationResponse = articleService.createArticle(articleCreationRequest)
        return ResponseEntity(articleCreationResponse, HttpStatus.OK)
    }

    @GetMapping
    fun getAllArticles(
            @RequestParam(name = "title"
                    , defaultValue = ""
                    , required = false) title: String,
            @PageableDefault(size = 10
                    , page = 0
                    , sort = ["title"]) pageable: Pageable
    ): ResponseEntity<ArticlesWithPaginationResponse> {
        val articleListResponse = if (title.isEmpty()) articleService.getArticlesWithPaginationAndSort(pageable)
        else articleService.getArticlesByTitleWithPaginationAndSort(title, pageable)
        return ResponseEntity(articleListResponse, HttpStatus.OK)
    }

    @PutMapping("{id}")
    fun updateArticle(
            @PathVariable(name = "id", required = true) id: String,
            @RequestBody articleUpdatingRequest: ArticleUpdatingRequest
    ): ResponseEntity<ArticleResponse> = ResponseEntity(
            articleService.updateArticle(articleUpdatingRequest, id),
            HttpStatus.OK)

    @DeleteMapping("{id}")
    fun deleteArticle(@PathVariable(name = "id", required = true) id: String): ResponseEntity<Void> {
        articleService.deleteArticle(id)
        return ResponseEntity(HttpStatus.OK)
    }

    @GetMapping("category/{id}")
    fun getArticlesByCategory(
            @PathVariable(name = "id", required = true) id: String,
            @PageableDefault(size = 10
                    , page = 0
                    , sort = ["title"]) pageable: Pageable
    ): ResponseEntity<ArticlesWithPaginationResponse> = ResponseEntity(
            articleService.getArticlesByCategory(id, pageable), HttpStatus.OK)

    @GetMapping("{id}")
    fun getArticle(@PathVariable(name = "id", required = true) id: String): ResponseEntity<ArticleResponse> {
        return ResponseEntity(articleService.getArticleById(id), HttpStatus.OK)
    }

    @GetMapping("tag/{id}")
    fun getArticlesByTagId(
            @PathVariable(name = "id", required = true) id: String,
            @PageableDefault(size = 10
                    , page = 0
                    , sort = ["title"]) pageable: Pageable
    ): ResponseEntity<ArticlesWithPaginationResponse> = ResponseEntity(
            articleService.getArticlesByTagId(id, pageable), HttpStatus.OK)

    @GetMapping("tag")
    fun getArticlesByTagLink(
            @RequestParam(name = "link", required = true) link: String,
            @PageableDefault(size = 10
                    , page = 0
                    , sort = ["title"]) pageable: Pageable
    ): ResponseEntity<ArticlesWithPaginationResponse> = ResponseEntity(
            articleService.getArticlesByTagLink(link, pageable), HttpStatus.OK)
}