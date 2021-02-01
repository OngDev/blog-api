package com.ongdev.blog.api.controllers

import com.ongdev.blog.api.models.dtos.requests.CategoryCreationRequest
import com.ongdev.blog.api.models.dtos.requests.CategoryUpdatingRequest
import com.ongdev.blog.api.models.dtos.responses.CategoriesWithPaginationResponse
import com.ongdev.blog.api.models.dtos.responses.CategoryResponse
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
    fun getCategoryById(@PathVariable(name = "id", required = true) id: String): ResponseEntity<CategoryResponse> {
        val category = categoryService.getCategoryById(id)
        return ResponseEntity(category, HttpStatus.OK)
    }

    @GetMapping
    fun getAllCategories(@PageableDefault(size = 10, page = 0) pageable: Pageable): ResponseEntity<CategoriesWithPaginationResponse> {
        val categories = categoryService.getAllCategories(pageable)
        return ResponseEntity(categories, HttpStatus.OK)
    }

    @PostMapping(consumes = ["application/json"], produces = ["application/json"])
    fun createCategory(@RequestBody categoryCreationRequest: CategoryCreationRequest): ResponseEntity<CategoryResponse> {
        val category = categoryService.createCategory(categoryCreationRequest)
        return ResponseEntity(category, HttpStatus.OK)
    }

    @PutMapping("{id}", consumes = ["application/json"], produces = ["application/json"])
    fun updateCategory(@PathVariable(name = "id", required = true) id: String, @RequestBody categoryUpdatingRequest: CategoryUpdatingRequest): ResponseEntity<CategoryResponse> {
        val category = categoryService.updateCategoryById(categoryUpdatingRequest, id)
        return ResponseEntity(category, HttpStatus.OK)
    }

    @DeleteMapping("{id}")
    fun deleteCategory(@PathVariable(name = "id", required = true) id: String): ResponseEntity<Void> {
        categoryService.deleteCategoryById(id)
        return ResponseEntity(HttpStatus.OK)
    }

    @GetMapping("link/{link}")
    fun getCategoriesByLink(@PathVariable(name = "link", required = true) link: String
                            , @PageableDefault(size = 10, page = 0) pageable: Pageable): ResponseEntity<CategoriesWithPaginationResponse> {
        val categories = categoryService.getCategoriesByLink(link, pageable)
        return ResponseEntity(categories, HttpStatus.OK)
    }
}