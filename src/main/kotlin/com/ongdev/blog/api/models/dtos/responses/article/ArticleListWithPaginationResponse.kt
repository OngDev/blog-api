package com.ongdev.blog.api.models.dtos.responses.article

import org.springframework.data.domain.Page

class ArticleListWithPaginationResponse(val result: Page<ArticleCreationResponse>)
