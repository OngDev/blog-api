package com.ongdev.blog.api.services.impl

import com.ongdev.blog.api.exceptions.*
import com.ongdev.blog.api.models.appUtils
import com.ongdev.blog.api.models.dtos.requests.CategoryCreationRequest
import com.ongdev.blog.api.models.dtos.requests.CategoryUpdatingRequest
import com.ongdev.blog.api.models.dtos.responses.CategoriesWithPaginationResponse
import com.ongdev.blog.api.models.dtos.responses.CategoryResponse
import com.ongdev.blog.api.models.repositories.CategoryRepository
import com.ongdev.blog.api.models.toCategoryEntity
import com.ongdev.blog.api.models.toCategoryResponse
import com.ongdev.blog.api.models.toPageCategoryResponse
import com.ongdev.blog.api.services.interfaces.CategoryService
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.*

@Service
class CategoryServiceImpl(val categoryRepository: CategoryRepository) : CategoryService {

    override fun getCategoryById(id: String): CategoryResponse {
        val category = (categoryRepository.findById(UUID.fromString(id))).orElseThrow {
            EntityNotFoundException("category", "id", id)
        }
        return category.toCategoryResponse()
    }

    override fun getAllCategories(pageable: Pageable): CategoriesWithPaginationResponse {
        val categories = categoryRepository.findAll(pageable)
        val pageCategoryResponse = categories.toPageCategoryResponse()
        return CategoriesWithPaginationResponse(pageCategoryResponse)
    }

    override fun createCategory(categoryCreationRequest: CategoryCreationRequest): CategoryResponse {
        val linkRequest = appUtils.removeAccent(categoryCreationRequest.name)
        if (categoryRepository.existsByLink(linkRequest).not()) {
            val category = categoryCreationRequest.toCategoryEntity()
            try {
                return categoryRepository.save(category).toCategoryResponse()
            } catch (ex: IllegalArgumentException) {
                throw EntityCreationFailedException("category")
            }
        } else {
            throw EntityIsExistedException("category", "link", linkRequest)
        }
    }

    override fun updateCategoryById(categoryUpdatingRequest: CategoryUpdatingRequest, id: String): CategoryResponse {
        var category = categoryRepository.findById(UUID.fromString(id)).orElseThrow {
            EntityNotFoundException("category", "id", id)
        }
        val linkRequest = appUtils.removeAccent(categoryUpdatingRequest.name)
        if (categoryRepository.existsByLink(linkRequest).not() || (linkRequest == category.link)) {
            category = categoryUpdatingRequest.toCategoryEntity(category)
            try {
                return categoryRepository.save(category).toCategoryResponse()
            } catch (ex: IllegalArgumentException) {
                throw EntityUpdatingFailedException("category")
            }
        } else {
            throw EntityIsExistedException("category", "link", linkRequest)
        }
    }

    override fun deleteCategoryById(id: String) {
        val category = categoryRepository.findById(UUID.fromString(id)).orElseThrow {
            EntityNotFoundException("category", "id", id)
        }
        try {
            categoryRepository.delete(category)
        } catch (ex: IllegalArgumentException) {
            throw EntityDeletingFailedException("category")
        }
    }

    override fun getCategoriesByLink(link: String, pageable: Pageable): CategoriesWithPaginationResponse {
        val categories = categoryRepository.findAllByLink(link, pageable)
        val pageCategoryResponse = categories.toPageCategoryResponse()
        return CategoriesWithPaginationResponse(pageCategoryResponse)
    }
}