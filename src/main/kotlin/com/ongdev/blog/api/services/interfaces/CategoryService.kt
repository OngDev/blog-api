package com.ongdev.blog.api.services.interfaces

import com.ongdev.blog.api.models.dtos.requests.CategoryCreationRequest
import com.ongdev.blog.api.models.dtos.requests.CategoryUpdatingRequest
import com.ongdev.blog.api.models.dtos.responses.CategoriesWithPaginationResponse
import com.ongdev.blog.api.models.dtos.responses.CategoryResponse
import org.springframework.data.domain.Pageable

interface CategoryService {
    fun getCategoriesByLink(link: String, pageable: Pageable): CategoriesWithPaginationResponse
    fun getCategoryById(id: String): CategoryResponse
    fun getAllCategories(pageable: Pageable): CategoriesWithPaginationResponse
    fun createCategory(categoryCreationRequest: CategoryCreationRequest): CategoryResponse
    fun updateCategoryById(categoryUpdatingRequest: CategoryUpdatingRequest, id: String): CategoryResponse
    fun deleteCategoryById(id: String)
}