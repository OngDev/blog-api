package com.ongdev.blog.api

import com.ongdev.blog.api.exceptions.CategoryCreationFailedException
import com.ongdev.blog.api.exceptions.CategoryDeletingFailedException
import com.ongdev.blog.api.exceptions.CategoryNotFoundException
import com.ongdev.blog.api.exceptions.CategoryUpdatingFailedException
import com.ongdev.blog.api.models.dtos.requests.CategoryCreationRequest
import com.ongdev.blog.api.models.dtos.requests.CategoryUpdateRequest
import com.ongdev.blog.api.models.entities.Category
import com.ongdev.blog.api.models.repositories.CategoryRepository
import com.ongdev.blog.api.models.toCategoryEntity
import com.ongdev.blog.api.services.impl.CategoryServiceImpl
import com.ongdev.blog.api.services.interfaces.CategoryService
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito
import java.util.*

class CategoryServiceTests {

    private val categoryRepository: CategoryRepository = Mockito.mock(CategoryRepository::class.java)
    private var categoryService: CategoryService = CategoryServiceImpl(categoryRepository)

    private lateinit var mockCategoryCreationRequest: CategoryCreationRequest
    private lateinit var mockCategoryUpdatingRequest: CategoryUpdateRequest
    private lateinit var mockCategory: Category
    private lateinit var mockOptionalCategory: Optional<Category>

    @BeforeEach
    internal fun setUp() {
        mockCategoryCreationRequest = CategoryCreationRequest(
                "Test name"
        )
        mockCategoryUpdatingRequest = CategoryUpdateRequest(
                "Test updated name"
        )
        mockCategory = mockCategoryCreationRequest.toCategoryEntity()
        mockCategory.id = UUID.randomUUID()
        mockOptionalCategory = Optional.of(mockCategory)
    }

    @Test
    fun `Create Category, should return Category`() {
        Mockito.`when`(categoryRepository.save(Mockito.any(Category::class.java)))
                .thenReturn(mockCategory)

        val result = categoryService.createCategory(mockCategoryCreationRequest)

        Assertions.assertThat(result.id).isEqualTo(mockCategory.id.toString())
    }

    @Test
    fun `Create Category, should throw error when Category is null`() {
        Mockito.`when`(categoryRepository.save(Mockito.any(Category::class.java))).thenThrow(IllegalArgumentException())

        assertThrows<CategoryCreationFailedException> { categoryService.createCategory(mockCategoryCreationRequest) }
    }

    @Test
    fun `Update Category, should return updated Category`() {
        Mockito.`when`(categoryRepository.findById(Mockito.any(UUID::class.java))).thenReturn(mockOptionalCategory)
        Mockito.`when`(categoryRepository.save(Mockito.any(Category::class.java))).thenReturn(mockCategory)

        val result = categoryService.updateCategory(mockCategoryUpdatingRequest, UUID.randomUUID().toString())

        Assertions.assertThat(result.id).isEqualTo(mockCategory.id.toString())
    }

    @Test
    fun `Update Category, should throw error when failed to find entity`() {
        Mockito.`when`(categoryRepository.findById(Mockito.any(UUID::class.java))).thenThrow(CategoryNotFoundException())

        assertThrows<CategoryNotFoundException> { categoryService.updateCategory(mockCategoryUpdatingRequest, UUID.randomUUID().toString()) }
    }

    @Test
    fun `Update Category, should throw error when failed to save entity`() {
        Mockito.`when`(categoryRepository.findById(Mockito.any(UUID::class.java))).thenReturn(mockOptionalCategory)
        Mockito.`when`(categoryRepository.save(Mockito.any(Category::class.java))).thenThrow(IllegalArgumentException())

        assertThrows<CategoryUpdatingFailedException> { categoryService.updateCategory(mockCategoryUpdatingRequest, UUID.randomUUID().toString()) }
    }

    @Test
    fun `Get Category, should return a Category by id`() {
        Mockito.`when`(categoryRepository.findById(Mockito.any(UUID::class.java))).thenReturn(mockOptionalCategory)

        val result = categoryService.getCategory(UUID.randomUUID().toString())

        Assertions.assertThat(result.id).isEqualTo(mockCategory.id.toString())
    }

    @Test
    fun `Get Category, should throw error when failed to find entity`() {
        Mockito.`when`(categoryRepository.findById(Mockito.any(UUID::class.java))).thenThrow(CategoryNotFoundException())

        assertThrows<CategoryNotFoundException> { categoryService.getCategory(UUID.randomUUID().toString()) }
    }

    @Test
    fun `Delete Category, should throw error when failed to find entity`() {
        Mockito.`when`(categoryRepository.findById(Mockito.any(UUID::class.java))).thenThrow(CategoryNotFoundException())

        assertThrows<CategoryNotFoundException> { categoryService.deleteCategory(UUID.randomUUID().toString()) }
    }

    @Test
    fun `Delete Category, should throw error when failed to delete entity`() {
        Mockito.`when`(categoryRepository.findById(Mockito.any(UUID::class.java))).thenReturn(mockOptionalCategory)
        Mockito.`when`(categoryRepository.delete(Mockito.any(Category::class.java))).thenThrow(IllegalArgumentException())

        assertThrows<CategoryDeletingFailedException> { categoryService.deleteCategory(UUID.randomUUID().toString()) }
    }
}