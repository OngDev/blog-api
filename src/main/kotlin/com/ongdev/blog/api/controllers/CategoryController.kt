package com.ongdev.blog.api.controllers

import com.ongdev.blog.api.models.dtos.requests.CategoryCreationRequest
import com.ongdev.blog.api.models.dtos.requests.CategoryUpdateRequest
import com.ongdev.blog.api.models.dtos.responses.CategoryCreationResponse
import com.ongdev.blog.api.models.dtos.responses.CategoryListResponse
import com.ongdev.blog.api.models.entities.Category
import com.ongdev.blog.api.services.interfaces.CategoryService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/categories")
class CategoryController @Autowired constructor(private val categoryService: CategoryService) {

    @GetMapping("/{id}")
    fun getCategoryById(@PathVariable id: String): ResponseEntity<CategoryCreationResponse>{
        val category = categoryService.getCategoryById(id);
        return ResponseEntity(category, HttpStatus.OK)
    }

    @GetMapping("")
    fun getAllCategories(): ResponseEntity<CategoryListResponse>{
        val categories = categoryService.getAllCategories();
        return ResponseEntity(categories, HttpStatus.OK)
    }

    @PostMapping("/add")
    fun addCategory(@RequestBody categoryCreationRequest: CategoryCreationRequest): ResponseEntity<CategoryCreationResponse>{
        val categoryCreated = categoryService.createCategories(categoryCreationRequest);
        return ResponseEntity.ok(categoryCreated);
    }

    @PutMapping("update/{id}")
    fun updateCategory (@PathVariable id: String, @RequestBody categoryUpdateRequest: CategoryUpdateRequest): ResponseEntity<CategoryCreationResponse>{
        val categoryUpdated = categoryService.updateCategory(categoryUpdateRequest, id);
        return ResponseEntity.ok(categoryUpdated);
    }

    @DeleteMapping("delete/{id}")
    fun deleteCategory (@PathVariable id: String): ResponseEntity<Void>{
        categoryService.deleteCategory(id);
        return ResponseEntity(HttpStatus.OK)
    }




}