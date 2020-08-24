package com.ongdev.blog.api.services.interfaces

import com.ongdev.blog.api.models.dtos.requests.article.ArticleCreationRequest
import com.ongdev.blog.api.models.dtos.requests.article.ArticleUpdatingRequest
import com.ongdev.blog.api.models.dtos.responses.article.ArticleCreationResponse
import com.ongdev.blog.api.models.dtos.responses.article.ArticleListWithPaginationResponse
import com.ongdev.blog.api.models.dtos.responses.article.ArticleUpdatingResponse
import org.springframework.data.domain.Pageable

interface ArticleService {
    fun createArticle(articleCreationRequest: ArticleCreationRequest): ArticleCreationResponse
    fun getArticlesWithPaginationAndSort(pageable: Pageable): ArticleListWithPaginationResponse
    fun getArticlesByTitleWithPaginationAndSort(title: String, pageable: Pageable): ArticleListWithPaginationResponse
    fun updateArticle(articleUpdatingRequest: ArticleUpdatingRequest, id: String): ArticleUpdatingResponse
    fun deleteArticle(id: String)
    fun getListOfArticlesForEachCategory(name: String, pageable: Pageable): ArticleListWithPaginationResponse
}
