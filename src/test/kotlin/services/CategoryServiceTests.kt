package services

import com.ongdev.blog.api.exceptions.*
import com.ongdev.blog.api.models.dtos.requests.CategoryCreationRequest
import com.ongdev.blog.api.models.dtos.requests.CategoryUpdatingRequest
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
    private lateinit var mockCategoryUpdatingRequest: CategoryUpdatingRequest
    private lateinit var mockCategory: Category
    private lateinit var mockOptionalCategory: Optional<Category>

    @BeforeEach
    internal fun setUp() {
        mockCategoryCreationRequest = CategoryCreationRequest(
                "Test name"
        )
        mockCategoryUpdatingRequest = CategoryUpdatingRequest(
                "Test update name"
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
    fun `Create Category, should throw error when could not save`() {
        Mockito.`when`(categoryRepository.save(Mockito.any(Category::class.java))).thenThrow(IllegalArgumentException())

        assertThrows<EntityCreationFailedException> { categoryService.createCategory(mockCategoryCreationRequest) }
    }

    @Test
    fun `Create Category, should throw error when link is existed`() {
        Mockito.`when`(categoryRepository.existsByLink("test-name")).thenThrow(EntityIsExistedException::class.java)

        assertThrows<EntityIsExistedException> { categoryService.createCategory(mockCategoryCreationRequest) }
    }

    @Test
    fun `Update Category, should return updated Category`() {
        Mockito.`when`(categoryRepository.findById(Mockito.any(UUID::class.java))).thenReturn(mockOptionalCategory)
        Mockito.`when`(categoryRepository.save(Mockito.any(Category::class.java))).thenReturn(mockCategory)

        val result = categoryService.updateCategoryById(mockCategoryUpdatingRequest, UUID.randomUUID().toString())

        Assertions.assertThat(result.id).isEqualTo(mockCategory.id.toString())
    }

    @Test
    fun `Update Category, should throw error when link is existed`() {
        Mockito.`when`(categoryRepository.findById(Mockito.any(UUID::class.java))).thenReturn(mockOptionalCategory)
        Mockito.`when`(categoryRepository.existsByLink("test-update-name")).thenThrow(EntityIsExistedException::class.java)

        assertThrows<EntityIsExistedException> { categoryService.updateCategoryById(mockCategoryUpdatingRequest, UUID.randomUUID().toString()) }
    }

    @Test
    fun `Update Category, should throw error when failed to find entity`() {
        Mockito.`when`(categoryRepository.findById(Mockito.any(UUID::class.java))).thenThrow(EntityNotFoundException::class.java)

        assertThrows<EntityNotFoundException> { categoryService.updateCategoryById(mockCategoryUpdatingRequest, UUID.randomUUID().toString()) }
    }

    @Test
    fun `Update Category, should throw error when failed to save entity`() {
        Mockito.`when`(categoryRepository.findById(Mockito.any(UUID::class.java))).thenReturn(mockOptionalCategory)
        Mockito.`when`(categoryRepository.save(Mockito.any(Category::class.java))).thenThrow(IllegalArgumentException())

        assertThrows<EntityUpdatingFailedException> { categoryService.updateCategoryById(mockCategoryUpdatingRequest, UUID.randomUUID().toString()) }
    }

    @Test
    fun `Get Category, should return a Category by id`() {
        Mockito.`when`(categoryRepository.findById(Mockito.any(UUID::class.java))).thenReturn(mockOptionalCategory)

        val result = categoryService.getCategoryById(UUID.randomUUID().toString())

        Assertions.assertThat(result.id).isEqualTo(mockCategory.id.toString())
    }

    @Test
    fun `Get Category, should throw error when failed to find entity`() {
        Mockito.`when`(categoryRepository.findById(Mockito.any(UUID::class.java))).thenThrow(EntityNotFoundException::class.java)

        assertThrows<EntityNotFoundException> { categoryService.getCategoryById(UUID.randomUUID().toString()) }
    }

    @Test
    fun `Delete Category, should throw error when failed to find entity`() {
        Mockito.`when`(categoryRepository.findById(Mockito.any(UUID::class.java))).thenThrow(EntityNotFoundException::class.java)

        assertThrows<EntityNotFoundException> { categoryService.deleteCategoryById(UUID.randomUUID().toString()) }
    }

    @Test
    fun `Delete Category, should throw error when failed to delete entity`() {
        Mockito.`when`(categoryRepository.findById(Mockito.any(UUID::class.java))).thenReturn(mockOptionalCategory)
        Mockito.`when`(categoryRepository.delete(Mockito.any(Category::class.java))).thenThrow(IllegalArgumentException())

        assertThrows<EntityDeletingFailedException> { categoryService.deleteCategoryById(UUID.randomUUID().toString()) }
    }
}