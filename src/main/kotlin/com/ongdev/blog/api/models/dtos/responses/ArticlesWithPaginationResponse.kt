package com.ongdev.blog.api.models.dtos.responses

import org.springframework.data.domain.Page

class ArticlesWithPaginationResponse(val result: Page<ArticleResponse>)
