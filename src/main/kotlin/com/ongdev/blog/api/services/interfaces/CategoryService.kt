package com.ongdev.blog.api.services.interfaces

import com.ongdev.blog.api.models.dtos.requests.CategoryCreationRequest
import com.ongdev.blog.api.models.dtos.requests.CategoryUpdatingRequest
import com.ongdev.blog.api.models.dtos.responses.CategoryResponse
import com.ongdev.blog.api.models.dtos.responses.CategoriesWithPaginationResponse
import org.springframework.data.domain.Pageable

interface CategoryService {
    fun getCategoryById(id: String): CategoryResponse
    fun getAllCategories(pageable: Pageable): CategoriesWithPaginationResponse
    fun createCategory(categoryCreationRequest: CategoryCreationRequest): CategoryResponse
    fun updateCategoryById(categoryUpdatingRequest: CategoryUpdatingRequest, id: String): CategoryResponse
    fun deleteCategoryById(id: String)
}