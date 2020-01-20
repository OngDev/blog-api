package com.ongdev.blog.api.services.interfaces

import com.ongdev.blog.api.models.dtos.requests.ArticleCreationRequest
import com.ongdev.blog.api.models.dtos.responses.ArticleCreationResponse
import com.ongdev.blog.api.models.dtos.responses.ArticleListResponse
import com.ongdev.blog.api.models.dtos.responses.ArticleListWithPaginationResponse
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort

interface ArticleService {
    fun createArticle(articleCreationRequest: ArticleCreationRequest) : ArticleCreationResponse
    fun getArticlesWithPaginationAndSort(pageable: Pageable, sort: Sort) : ArticleListWithPaginationResponse
    fun getAllArticles() : ArticleListResponse
}
