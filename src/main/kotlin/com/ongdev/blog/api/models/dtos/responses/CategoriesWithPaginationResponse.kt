package com.ongdev.blog.api.models.dtos.responses

import org.springframework.data.domain.Page

class CategoriesWithPaginationResponse(val result: Page<CategoryResponse>)