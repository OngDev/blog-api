package com.ongdev.blog.api.services.interfaces

import com.ongdev.blog.api.models.dtos.requests.CategoryCreationRequest
import com.ongdev.blog.api.models.dtos.requests.CategoryUpdateRequest
import com.ongdev.blog.api.models.dtos.responses.CategoryCreationResponse
import com.ongdev.blog.api.models.dtos.responses.CategoryListWithPaginationResponse
import org.springframework.data.domain.Pageable

interface CategoryService {
    fun getCategory(id: String): CategoryCreationResponse
    fun getAllCategories(pageable: Pageable): CategoryListWithPaginationResponse
    fun createCategory(categoryCreationRequest: CategoryCreationRequest): CategoryCreationResponse
    fun updateCategory(categoryUpdateRequest: CategoryUpdateRequest, id: String): CategoryCreationResponse
    fun deleteCategory(id: String)
}