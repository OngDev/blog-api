package com.ongdev.blog.api.services.interfaces

import com.ongdev.blog.api.models.dtos.requests.ArticleCreationRequest
import com.ongdev.blog.api.models.dtos.requests.ArticleUpdatingRequest
import com.ongdev.blog.api.models.dtos.responses.ArticleCreationResponse
import com.ongdev.blog.api.models.dtos.responses.ArticleListWithPaginationResponse
import com.ongdev.blog.api.models.dtos.responses.ArticleUpdatingResponses
import com.ongdev.blog.api.models.entities.Article
import org.springframework.data.domain.Pageable

interface ArticleService {
    fun createArticle(articleCreationRequest: ArticleCreationRequest) : ArticleCreationResponse
    fun getArticlesWithPaginationAndSort(pageable: Pageable) : ArticleListWithPaginationResponse
    fun getArticlesByTitleWithPaginationAndSort(title: String, pageable: Pageable) : ArticleListWithPaginationResponse
    fun updateArticle(articleUpdatingRequest: ArticleUpdatingRequest, id: String) : ArticleUpdatingResponses
    fun deleteArticle(id: String)
    fun getAllStoriesWithTag(name:String): List<Article>
}
