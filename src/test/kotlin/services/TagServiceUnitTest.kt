package services


import com.ongdev.blog.api.exceptions.EntityCreationFailedException
import com.ongdev.blog.api.exceptions.EntityDeletingFailedException
import com.ongdev.blog.api.exceptions.EntityNotFoundException
import com.ongdev.blog.api.exceptions.EntityUpdatingFailedException
import com.ongdev.blog.api.models.dtos.requests.TagRequest
import com.ongdev.blog.api.models.entities.Tag
import com.ongdev.blog.api.models.repositories.TagRepository
import com.ongdev.blog.api.models.toTag
import com.ongdev.blog.api.services.impl.TagServiceImpl
import com.ongdev.blog.api.services.interfaces.TagService
import org.junit.Assert.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.springframework.data.domain.*
import java.util.*


internal class TagServiceUnitTest {
    private val tagRepository: TagRepository = Mockito.mock(TagRepository::class.java)

    private val tagService: TagService = TagServiceImpl(tagRepository)

    private lateinit var tagRequest: TagRequest
    private lateinit var tagUpdatingRequest: TagRequest
    private lateinit var tag: Tag
    private lateinit var tagUpdated: Tag
    private lateinit var tagPagedAll: Page<Tag>
    private lateinit var tagPagedByLink: Page<Tag>
    private lateinit var pageable: Pageable

    @BeforeEach
    internal fun setUp() {
        tagRequest = TagRequest("Thử nghiệm")
        tag = tagRequest.toTag()
        tag.id = UUID.randomUUID()

        tagUpdatingRequest = TagRequest("Thử nghiệm 2")
        tagUpdated = tagUpdatingRequest.toTag()
        tagUpdated.id = UUID.randomUUID()

        pageable = PageRequest.of(0, 30, Sort.Direction.DESC, "createAt")

        tagPagedAll = PageImpl<Tag>(listOf(tag, tagUpdated), pageable, 2)
        tagPagedByLink = PageImpl<Tag>(listOf(tag), pageable, 1)
    }

    @Test
    fun addTag_shouldSucceed() {
        `when`(tagRepository.save(any(Tag::class.java))).thenReturn(tag)

        val result = tagService.addTag(tagRequest)
        assertEquals("thu-nghiem", result.link)
    }

    @Test
    fun addTag_shouldThrow_CreationFailedException() {
        `when`(tagRepository.save(Mockito.any(Tag::class.java))).thenThrow(EntityCreationFailedException::class.java)

        assertThrows<EntityCreationFailedException> {
            tagService.addTag(tagRequest)
        }
    }

    @Test
    fun getTagById_shouldSucceed() {
        `when`(tagRepository.findById(any(UUID::class.java))).thenReturn(Optional.of(tag))

        val result = tagService.getTagById(UUID.randomUUID().toString())
        assertEquals("thu-nghiem", result.link)
    }

    @Test
    fun getTagById_shouldThrow_NotFoundException() {
        `when`(tagRepository.findById(any(UUID::class.java))).thenThrow(EntityNotFoundException::class.java)

        assertThrows<EntityNotFoundException> {
            tagService.getTagById(UUID.randomUUID().toString())
        }
    }

    @Test
    fun getAllTagByLink_shouldSucceed() {
        `when`(tagRepository.findAllByLink(tag.link, pageable)).thenReturn(tagPagedByLink)

        val allTagByLink = tagService.getAllTagByLink(tag.link, pageable)
        assertEquals(1, allTagByLink.totalElements)
    }

    @Test
    fun getAllTag_shouldSucceed() {
        `when`(tagRepository.findAll(any(Pageable::class.java))).thenReturn(tagPagedAll)

        val allTag = tagService.getAllTag(pageable)
        assertEquals(30, allTag.size)
        assertEquals(2, allTag.totalElements)
        assertEquals(0, allTag.page)
    }

    @Test
    fun updateTagById_shouldSucceed() {
        `when`(tagRepository.findById(any(UUID::class.java))).thenReturn(Optional.of(tag))
        `when`(tagRepository.save(Mockito.any(Tag::class.java))).thenReturn(tagUpdated)

        val tagUpdated = tagService.updateTagById(UUID.randomUUID().toString(), tagUpdatingRequest)
        assertEquals("thu-nghiem-2", tagUpdated.link)
    }

    @Test
    fun updateTagById_shouldThrow_NotFoundException() {
        `when`(tagRepository.findById(any(UUID::class.java))).thenReturn(Optional.empty())

        assertThrows<EntityNotFoundException> {
            tagService.updateTagById(UUID.randomUUID().toString(), tagUpdatingRequest)
        }
    }

    @Test
    fun updateTagById_shouldThrow_UpdatingFailedException() {
        `when`(tagRepository.findById(any(UUID::class.java))).thenReturn(Optional.of(tag))
        `when`(tagRepository.save(Mockito.any(Tag::class.java))).thenThrow(EntityUpdatingFailedException::class.java)

        assertThrows<EntityUpdatingFailedException> {
            tagService.updateTagById(UUID.randomUUID().toString(), tagUpdatingRequest)
        }
    }

    @Test
    fun deleteTagById_shouldSucceed() {
        `when`(tagRepository.existsById(any(UUID::class.java))).thenReturn(true)
        `when`(tagRepository.deleteById(UUID.randomUUID())).then {
            // Do nothing
        }

        tagService.deleteTagById(UUID.randomUUID().toString())
        assertDoesNotThrow { EntityNotFoundException("tag", "id", UUID.randomUUID().toString()) }
    }

    fun deleteTagById_shouldThrow_NotFoundException() {
        `when`(tagRepository.existsById(any(UUID::class.java))).thenReturn(false)

        assertThrows<EntityNotFoundException> {
            tagService.deleteTagById(UUID.randomUUID().toString())
        }
    }

    fun deleteTagById_shouldThrow_DeletingFailedException() {
        `when`(tagRepository.existsById(any(UUID::class.java))).thenReturn(true)
        `when`(tagRepository.deleteById(UUID.randomUUID())).thenThrow(EntityDeletingFailedException::class.java)

        assertThrows<EntityDeletingFailedException> {
            tagService.deleteTagById(UUID.randomUUID().toString())
        }
    }

}