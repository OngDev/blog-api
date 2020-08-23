package com.ongdev.blog.api.services.impl

import com.ongdev.blog.api.models.dtos.requests.Category.CategoryCreationRequest
import com.ongdev.blog.api.models.dtos.responses.category.CategoryCreationResponse
import com.ongdev.blog.api.models.dtos.responses.category.CategoryListWithPaginationResponse
import com.ongdev.blog.api.models.repositories.CategoryRepository
import com.ongdev.blog.api.models.toCategoryCreationResponse
import com.ongdev.blog.api.models.toCategoryEntity
import com.ongdev.blog.api.services.interfaces.CategoryService
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class CategoryServiceImpl(val categoryRepository: CategoryRepository) : CategoryService {


    override fun createCategory(categoryCreationRequest: CategoryCreationRequest): CategoryCreationResponse {
        val category = categoryCreationRequest.toCategoryEntity()
        return categoryRepository.save(category).toCategoryCreationResponse()
    }

    override fun getCategoriesWithPaginationAndSort(pageable: Pageable): CategoryListWithPaginationResponse {
        val categories = categoryRepository.findAll(pageable)
        val categoryListResponseContent = categories.map {
            it.toCategoryCreationResponse()
        }
        return CategoryListWithPaginationResponse(categoryListResponseContent)
    }

    override fun getCategoriesByNameWithPaginationAndSort(name: String, pageable: Pageable): CategoryListWithPaginationResponse {
        val categories = categoryRepository.findAllByName(name, pageable)
        val categoryListResponseContent = categories.map {
            it.toCategoryCreationResponse()
        }
        return CategoryListWithPaginationResponse(categoryListResponseContent)
    }

}