package com.ongdev.blog.api.services.interfaces

import com.ongdev.blog.api.models.dtos.requests.CategoryCreationRequest
import com.ongdev.blog.api.models.dtos.requests.CategoryUpdateRequest
import com.ongdev.blog.api.models.dtos.responses.CategoryCreationResponse
import com.ongdev.blog.api.models.dtos.responses.CategoryListResponse


interface CategoryService{
    fun getCategoryById(id: String): CategoryCreationResponse
    fun getAllCategories(): CategoryListResponse
    fun createCategories(categoryCreationRequest: CategoryCreationRequest): CategoryCreationResponse
    fun updateCategory(categoryUpdateRequest: CategoryUpdateRequest, id: String): CategoryCreationResponse
    fun deleteCategory(id: String)
}