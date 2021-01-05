package com.ongdev.blog.api.services.impl

import com.ongdev.blog.api.exceptions.*
import com.ongdev.blog.api.models.dtos.requests.CategoryCreationRequest
import com.ongdev.blog.api.models.dtos.requests.CategoryUpdateRequest
import com.ongdev.blog.api.models.dtos.responses.CategoryCreationResponse
import com.ongdev.blog.api.models.dtos.responses.CategoryListWithPaginationResponse
import com.ongdev.blog.api.models.repositories.CategoryRepository
import com.ongdev.blog.api.models.toCategory
import com.ongdev.blog.api.models.toCategoryCreationResponse
import com.ongdev.blog.api.models.toCategoryEntity
import com.ongdev.blog.api.models.toPageCategoryResponse
import com.ongdev.blog.api.services.interfaces.CategoryService
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.*

@Service
class CategoryServiceImpl(val categoryRepository: CategoryRepository) : CategoryService {

    override fun getCategory(id: String): CategoryCreationResponse {
        val category = (categoryRepository.findById(UUID.fromString(id))).orElseThrow {
            EntityNotFoundException("category", "id", id)
        }
        return category.toCategoryCreationResponse()
    }

    override fun getAllCategories(pageable: Pageable): CategoryListWithPaginationResponse {
        val categories = categoryRepository.findAll(pageable)
        val pageCategoryResponse = categories.toPageCategoryResponse()
        return CategoryListWithPaginationResponse(pageCategoryResponse)
    }

    override fun createCategory(categoryCreationRequest: CategoryCreationRequest): CategoryCreationResponse {
        if (categoryRepository.existsByName(categoryCreationRequest.name).not()) {
            val category = categoryCreationRequest.toCategoryEntity()
            try {
                return categoryRepository.save(category).toCategoryCreationResponse()
            } catch (ex: IllegalArgumentException) {
                throw EntityCreationFailedException("category")
            }
        } else {
            throw EntityIsExistedException("category", "name", categoryCreationRequest.name)
        }
    }

    override fun updateCategory(categoryUpdateRequest: CategoryUpdateRequest, id: String): CategoryCreationResponse {
        if (categoryRepository.existsByName(categoryUpdateRequest.name).not()) {
            var category = categoryRepository.findById(UUID.fromString(id)).orElseThrow {
                EntityNotFoundException("category", "id", id)
            }
            category = categoryUpdateRequest.toCategory(category)
            try {
                return categoryRepository.save(category).toCategoryCreationResponse()
            } catch (ex: IllegalArgumentException) {
                throw EntityUpdatingFailedException("category")
            }
        } else {
            throw EntityIsExistedException("category", "name", categoryUpdateRequest.name)
        }
    }

    override fun deleteCategory(id: String) {
        val category = categoryRepository.findById(UUID.fromString(id)).orElseThrow {
            EntityNotFoundException("category", "id", id)
        }
        try {
            categoryRepository.delete(category)
        } catch (ex: IllegalArgumentException) {
            throw EntityDeletingFailedException("category")
        }
    }
}