package com.ongdev.blog.api.services.interfaces

import com.ongdev.blog.api.models.dtos.requests.ArticleCreationRequest
import com.ongdev.blog.api.models.dtos.requests.ArticleUpdatingRequest
import com.ongdev.blog.api.models.dtos.responses.ArticleResponse
import com.ongdev.blog.api.models.dtos.responses.ArticlesWithPaginationResponse
import org.springframework.data.domain.Pageable

interface ArticleService {
    fun createArticle(articleCreationRequest: ArticleCreationRequest): ArticleResponse
    fun getArticlesWithPaginationAndSort(pageable: Pageable): ArticlesWithPaginationResponse
    fun getArticlesByTitleWithPaginationAndSort(title: String, pageable: Pageable): ArticlesWithPaginationResponse
    fun updateArticle(articleUpdatingRequest: ArticleUpdatingRequest, id: String): ArticleResponse
    fun deleteArticle(id: String)
    fun getArticlesByCategory(id: String, pageable: Pageable): ArticlesWithPaginationResponse
    fun getArticleById(id: String): ArticleResponse
    fun getArticlesByTagId(id: String, pageable: Pageable): ArticlesWithPaginationResponse
    fun getArticlesByTagLink(link: String, pageable: Pageable): ArticlesWithPaginationResponse
    fun getArticlesByLink(link: String, pageable: Pageable): ArticlesWithPaginationResponse
}
