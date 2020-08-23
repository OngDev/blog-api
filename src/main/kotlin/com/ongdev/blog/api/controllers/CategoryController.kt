package com.ongdev.blog.api.controllers

import com.ongdev.blog.api.models.dtos.requests.Category.CategoryCreationRequest
import com.ongdev.blog.api.models.dtos.responses.category.CategoryCreationResponse
import com.ongdev.blog.api.models.dtos.responses.category.CategoryListWithPaginationResponse
import com.ongdev.blog.api.models.entities.Category
import com.ongdev.blog.api.services.interfaces.CategoryService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/category")
class CategoryController @Autowired constructor(private val categoryService: CategoryService){


    @PostMapping
    fun createArticle(@RequestBody categoryCreationRequest: CategoryCreationRequest): ResponseEntity<CategoryCreationResponse> {
        val categoryCreationResponse = categoryService.createCategory(categoryCreationRequest)
        return ResponseEntity(categoryCreationResponse, HttpStatus.OK)
    }

    @GetMapping
    fun getAllArticles(
            @RequestParam(name = "name", defaultValue = "", required = false) name: String,
            pageable: Pageable
    ): ResponseEntity<CategoryListWithPaginationResponse> {
        val categoryListResponse = if (name.isEmpty()) categoryService.getCategoriesWithPaginationAndSort(pageable)
        else categoryService.getCategoriesByNameWithPaginationAndSort(name, pageable)
        return ResponseEntity(categoryListResponse, HttpStatus.OK)
    }
}
