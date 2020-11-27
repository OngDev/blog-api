package com.ongdev.blog.api.services.interfaces

import com.ongdev.blog.api.models.dtos.requests.ArticleCreationRequest
import com.ongdev.blog.api.models.dtos.requests.ArticleUpdatingRequest
import com.ongdev.blog.api.models.dtos.responses.ArticleCreationResponse
import com.ongdev.blog.api.models.dtos.responses.ArticleListWithPaginationResponse
import com.ongdev.blog.api.models.dtos.responses.ArticleUpdatingResponse
import org.springframework.data.domain.Pageable

interface ArticleService {
    fun createArticle(articleCreationRequest: ArticleCreationRequest) : ArticleCreationResponse
    fun getArticlesWithPaginationAndSort(pageable: Pageable) : ArticleListWithPaginationResponse
    fun getArticlesByTitleWithPaginationAndSort(title: String, pageable: Pageable) : ArticleListWithPaginationResponse
    fun updateArticle(articleUpdatingRequest: ArticleUpdatingRequest, id: String) : ArticleUpdatingResponse
    fun deleteArticle(id: String)
    fun getArticlesByCategory(id: String, pageable: Pageable): ArticleListWithPaginationResponse
    fun getArticleById(id: String): ArticleCreationResponse
}
