package com.ongdev.blog.api.services.interfaces

import com.ongdev.blog.api.models.dtos.requests.ArticleCreationRequest
import com.ongdev.blog.api.models.dtos.responses.ArticleCreationResponse

interface ArticleService {
    fun createArticle(articleCreationRequest: ArticleCreationRequest) : ArticleCreationResponse
}
