package com.ongdev.blog.api.services.impl

import com.ongdev.blog.api.exceptions.CategoryCreationFailedException
import com.ongdev.blog.api.exceptions.CategoryDeletingFailedException
import com.ongdev.blog.api.exceptions.CategoryNotFoundException
import com.ongdev.blog.api.exceptions.CategoryUpdatingFailedException
import com.ongdev.blog.api.models.dtos.requests.CategoryCreationRequest
import com.ongdev.blog.api.models.dtos.requests.CategoryUpdateRequest
import com.ongdev.blog.api.models.dtos.responses.CategoryCreationResponse
import com.ongdev.blog.api.models.dtos.responses.CategoryListWithPaginationResponse
import com.ongdev.blog.api.models.repositories.CategoryRepository
import com.ongdev.blog.api.models.toCategory
import com.ongdev.blog.api.models.toCategoryCreationResponse
import com.ongdev.blog.api.models.toCategoryEntity
import com.ongdev.blog.api.services.interfaces.CategoryService
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.*

@Service
class CategoryServiceImpl(val categoryRepository: CategoryRepository) : CategoryService {

    override fun getCategoryById(id: String): CategoryCreationResponse {
        val category = (categoryRepository.findById(UUID.fromString(id))).orElseThrow {
            CategoryNotFoundException()
        }
        return category.toCategoryCreationResponse()
    }

    override fun getAllCategories(pageable: Pageable): CategoryListWithPaginationResponse {
        val categories = categoryRepository.findAll(pageable)
        val categoriesResponseContent = categories.map {
            it.toCategoryCreationResponse()
        }
        return CategoryListWithPaginationResponse(categoriesResponseContent)
    }

    override fun createCategory(categoryCreationRequest: CategoryCreationRequest): CategoryCreationResponse {
        val category = categoryCreationRequest.toCategoryEntity()
        try {
            return categoryRepository.save(category).toCategoryCreationResponse()
        } catch (ex: IllegalArgumentException) {
            throw CategoryCreationFailedException()
        }
    }

    override fun updateCategory(categoryUpdateRequest: CategoryUpdateRequest, id: String): CategoryCreationResponse {
        var category = categoryRepository.findById(UUID.fromString(id)).orElseThrow {
            CategoryNotFoundException()
        }
        category = categoryUpdateRequest.toCategory(category)
        try {
            return categoryRepository.save(category).toCategoryCreationResponse()
        } catch (ex: IllegalArgumentException) {
            throw CategoryUpdatingFailedException()
        }
    }

    override fun deleteCategory(id: String) {
        val category = categoryRepository.findById(UUID.fromString(id)).orElseThrow {
            CategoryNotFoundException()
        }
        try {
            categoryRepository.delete(category)
        } catch (ex: IllegalArgumentException) {
            throw CategoryDeletingFailedException()
        }
    }
}