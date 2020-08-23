package com.ongdev.blog.api.services.interfaces

import com.ongdev.blog.api.models.dtos.requests.Category.CategoryCreationRequest
import com.ongdev.blog.api.models.dtos.responses.category.CategoryCreationResponse
import com.ongdev.blog.api.models.dtos.responses.category.CategoryListWithPaginationResponse
import org.springframework.data.domain.Pageable

interface CategoryService {
    fun createCategory(categoryCreationRequest: CategoryCreationRequest): CategoryCreationResponse
    fun getCategoriesWithPaginationAndSort(pageable: Pageable): CategoryListWithPaginationResponse
    fun getCategoriesByNameWithPaginationAndSort(name: String, pageable: Pageable): CategoryListWithPaginationResponse
}