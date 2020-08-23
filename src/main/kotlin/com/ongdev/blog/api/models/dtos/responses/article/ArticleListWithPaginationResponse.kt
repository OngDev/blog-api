package com.ongdev.blog.api.models.dtos.responses.article

import com.ongdev.blog.api.models.dtos.responses.article.ArticleCreationResponse
import org.springframework.data.domain.Page

class ArticleListWithPaginationResponse(val result: Page<ArticleCreationResponse>)
