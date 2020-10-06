package com.ongdev.blog.api.controllers

import com.ongdev.blog.api.models.dtos.requests.ArticleCreationRequest
import com.ongdev.blog.api.models.dtos.requests.ArticleUpdatingRequest
import com.ongdev.blog.api.models.dtos.responses.ArticleCreationResponse
import com.ongdev.blog.api.models.dtos.responses.ArticleListWithPaginationResponse
import com.ongdev.blog.api.models.dtos.responses.ArticleUpdatingResponse
import com.ongdev.blog.api.services.interfaces.ArticleService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/articles")
class ArticleController constructor(private val articleService : ArticleService) {

    @PostMapping
    fun createArticle(@RequestBody articleCreationRequest: ArticleCreationRequest) : ResponseEntity<ArticleCreationResponse> {
        val articleCreationResponse = articleService.createArticle(articleCreationRequest)
        return ResponseEntity(articleCreationResponse, HttpStatus.OK)
    }

    @GetMapping
    fun getAllArticles(
        @RequestParam(name = "title", defaultValue = "", required = false) title : String,
        pageable: Pageable
    ) : ResponseEntity<ArticleListWithPaginationResponse> {
        val articleListResponse = if (title.isEmpty()) articleService.getArticlesWithPaginationAndSort(pageable)
                                    else articleService.getArticlesByTitleWithPaginationAndSort(title, pageable)
        return ResponseEntity(articleListResponse, HttpStatus.OK)
    }

    @PutMapping
    fun updateArticle(
            @RequestParam(name = "id", required = true) id: String,
            @RequestBody articleUpdatingRequest: ArticleUpdatingRequest
    ) : ResponseEntity<ArticleUpdatingResponse> = ResponseEntity(
            articleService.updateArticle(articleUpdatingRequest, id),
            HttpStatus.OK)

    @DeleteMapping("/{id}")
    fun deleteArticle(@PathVariable(name = "id", required = true) id: String) : ResponseEntity<Void> {
        articleService.deleteArticle(id)
        return ResponseEntity(HttpStatus.OK)
    }

    @GetMapping("/category/{id}")
    fun getListArticlesForEachCategory(
            @PathVariable(name = "id", required = true) id: String,
            @PageableDefault(size = 10) pageable: Pageable
    ): ResponseEntity<ArticleListWithPaginationResponse> = ResponseEntity(
            articleService.getListArticlesByCategory(id, pageable), HttpStatus.OK)
}