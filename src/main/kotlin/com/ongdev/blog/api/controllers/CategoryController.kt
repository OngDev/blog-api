package com.ongdev.blog.api.controllers

import com.ongdev.blog.api.models.dtos.requests.CategoryCreationRequest
import com.ongdev.blog.api.models.dtos.requests.CategoryUpdateRequest
import com.ongdev.blog.api.models.dtos.responses.CategoryCreationResponse
import com.ongdev.blog.api.models.dtos.responses.CategoryListWithPaginationResponse
import com.ongdev.blog.api.services.interfaces.CategoryService
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("categories")
class CategoryController(private val categoryService: CategoryService) {

    @GetMapping("{id}")
    fun getCategoryById(@PathVariable(name = "id", required = true) id: String): ResponseEntity<CategoryCreationResponse> {
        val category = categoryService.getCategoryById(id)
        return ResponseEntity(category, HttpStatus.OK)
    }

    @GetMapping
    fun getAllCategories(@PageableDefault(size = 10) pageable: Pageable): ResponseEntity<CategoryListWithPaginationResponse> {
        val categories = categoryService.getAllCategories(pageable)
        return ResponseEntity(categories, HttpStatus.OK)
    }

    @PostMapping
    fun createCategory(@RequestBody categoryCreationRequest: CategoryCreationRequest): ResponseEntity<CategoryCreationResponse> {
        val category = categoryService.createCategory(categoryCreationRequest)
        return ResponseEntity(category, HttpStatus.OK)
    }

    @PutMapping("{id}")
    fun updateCategory(@PathVariable(name = "id", required = true) id: String, @RequestBody categoryUpdateRequest: CategoryUpdateRequest): ResponseEntity<CategoryCreationResponse> {
        val category = categoryService.updateCategory(categoryUpdateRequest, id)
        return ResponseEntity(category, HttpStatus.OK)
    }

    @DeleteMapping("{id}")
    fun deleteCategory(@PathVariable(name = "id", required = true) id: String): ResponseEntity<Void> {
        categoryService.deleteCategory(id)
        return ResponseEntity(HttpStatus.OK)
    }
}