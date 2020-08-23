package com.ongdev.blog.api.services.interfaces

import com.ongdev.blog.api.models.dtos.requests.ArticleCreationRequest
import com.ongdev.blog.api.models.dtos.requests.ArticleUpdatingRequest
import com.ongdev.blog.api.models.dtos.responses.ArticleCreationResponse
import com.ongdev.blog.api.models.dtos.responses.ArticleListWithPaginationResponse
import com.ongdev.blog.api.models.dtos.responses.ArticleUpdatingResponse
import org.springframework.data.domain.Pageable
import org.springframework.security.access.prepost.PreAuthorize

interface ArticleService {
    @PreAuthorize("hasAnyRole('ONGDEV')")
    fun createArticle(articleCreationRequest: ArticleCreationRequest) : ArticleCreationResponse
    @PreAuthorize("hasAnyRole('USER', 'ONGDEV')")
    fun getArticlesWithPaginationAndSort(pageable: Pageable) : ArticleListWithPaginationResponse
    @PreAuthorize("hasAnyRole('USER', 'ONGDEV')")
    fun getArticlesByTitleWithPaginationAndSort(title: String, pageable: Pageable) : ArticleListWithPaginationResponse
    @PreAuthorize("hasAnyRole('ONGDEV')")
    fun updateArticle(articleUpdatingRequest: ArticleUpdatingRequest, id: String) : ArticleUpdatingResponse
    @PreAuthorize("hasAnyRole('ONGDEV')")
    fun deleteArticle(id: String)
}
