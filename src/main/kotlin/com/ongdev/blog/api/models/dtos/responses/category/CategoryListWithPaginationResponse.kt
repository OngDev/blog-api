package com.ongdev.blog.api.models.dtos.responses.category

import org.springframework.data.domain.Page

class CategoryListWithPaginationResponse(val result: Page<CategoryCreationResponse>)