package com.ongdev.blog.api.services.impl

import com.ongdev.blog.api.models.dtos.requests.CategoryCreationRequest
import com.ongdev.blog.api.models.dtos.requests.CategoryUpdateRequest
import com.ongdev.blog.api.models.dtos.responses.CategoryCreationResponse
import com.ongdev.blog.api.models.dtos.responses.CategoryListResponse
import com.ongdev.blog.api.models.mapToCategory
import com.ongdev.blog.api.models.repositories.CategoryRepository
import com.ongdev.blog.api.models.toCategoryCreationResponse
import com.ongdev.blog.api.models.toCategoryEntity
import com.ongdev.blog.api.services.interfaces.CategoryService
import org.springframework.stereotype.Service
import java.util.*
import kotlin.RuntimeException

@Service
class CategoryServiceImpl(val categoryRepository: CategoryRepository): CategoryService {

    override fun getCategoryById(id: String): CategoryCreationResponse {
        val category = (categoryRepository.findById(UUID.fromString(id))).orElse(null);
        return category.toCategoryCreationResponse();
    }

    override fun getAllCategories(): CategoryListResponse {
        val categories = categoryRepository.findAll();
        val categoriesResponseContent = categories.map {
            it.toCategoryCreationResponse()
        }
        return CategoryListResponse(categoriesResponseContent);
    }

    override fun createCategories(categoryCreationRequest: CategoryCreationRequest) : CategoryCreationResponse {
       val category = categoryCreationRequest.toCategoryEntity();
       try{
           return categoryRepository.save(category).toCategoryCreationResponse()
       } catch (ex: IllegalArgumentException){
           throw RuntimeException()
       }
    }

    override fun updateCategory(categoryUpdateRequest: CategoryUpdateRequest, id: String): CategoryCreationResponse {
        val optionalCategory = categoryRepository.findById(UUID.fromString(id))
        if(!optionalCategory.isPresent) throw RuntimeException()
        var category = optionalCategory.get()
        category = categoryUpdateRequest.mapToCategory(category)
        try{
            return categoryRepository.save(category).toCategoryCreationResponse()
        } catch (ex: java.lang.IllegalArgumentException){
            throw RuntimeException()
        }

    }

    override fun deleteCategory(id: String) {
      val optionalCategory = categoryRepository.findById(UUID.fromString(id))
      if(!optionalCategory.isPresent) throw RuntimeException()
      try{
          return categoryRepository.delete(optionalCategory.get());
      } catch (ex: IllegalArgumentException){
          throw RuntimeException()
      }

    }


}