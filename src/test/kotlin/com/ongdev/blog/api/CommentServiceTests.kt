package com.ongdev.blog.api

import com.ongdev.blog.api.exceptions.CommentCreationFailedException
import com.ongdev.blog.api.exceptions.CommentDeletingFailedException
import com.ongdev.blog.api.exceptions.CommentNotFoundException
import com.ongdev.blog.api.exceptions.CommentUpdatingFailedException
import com.ongdev.blog.api.models.dtos.requests.CommentCreationRequest
import com.ongdev.blog.api.models.dtos.requests.CommentUpdatingRequest
import com.ongdev.blog.api.models.entities.Article
import com.ongdev.blog.api.models.entities.Comment
import com.ongdev.blog.api.models.repositories.CommentRepository
import com.ongdev.blog.api.models.toCommentEntity
import com.ongdev.blog.api.services.impl.CommentServiceImpl
import com.ongdev.blog.api.services.interfaces.CommentService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.*
import java.util.*

class CommentServiceTests {
    private val commentRepository: CommentRepository = mock(CommentRepository::class.java)

    private var commentService: CommentService = CommentServiceImpl(commentRepository)

    private lateinit var mockCommentCreationRequest: CommentCreationRequest
    private lateinit var mockCommentUpdatingRequest: CommentUpdatingRequest
    private lateinit var mockComment: Comment
    private lateinit var mockOptionalComment: Optional<Comment>

    @BeforeEach
    internal fun setUp() {
        mockCommentCreationRequest = CommentCreationRequest(
                "Test content",
                null,
                Article(),
                null,
                null
        )
        mockCommentUpdatingRequest = CommentUpdatingRequest(
                "Test updated content",
                null
        )
        mockComment = mockCommentCreationRequest.toCommentEntity()
        mockComment.id = UUID.randomUUID()
        mockOptionalComment = Optional.of(mockComment)
    }

    @Test
    fun `Create comment, should return comment`() {
        `when`(commentRepository.save(any(Comment::class.java)))
                .thenReturn(mockComment)

        val result = commentService.createComment(mockCommentCreationRequest)

        assertThat(result.id).isEqualTo(mockComment.id.toString())
    }

    @Test
    fun `Create comment, should throw error when comment is null`() {
        `when`(commentRepository.save(any(Comment::class.java))).thenThrow(IllegalArgumentException())

        assertThrows<CommentCreationFailedException> { commentService.createComment(mockCommentCreationRequest) }
    }

    @Test
    fun `Update comment, should return updated comment`() {
        `when`(commentRepository.findById(any(UUID::class.java))).thenReturn(mockOptionalComment)
        `when`(commentRepository.save(any(Comment::class.java))).thenReturn(mockComment)

        val result = commentService.updateComment(mockCommentUpdatingRequest, UUID.randomUUID().toString())

        assertThat(result.id).isEqualTo(mockComment.id.toString())
    }

    @Test
    fun `Update comment, should throw error when failed to find entity`() {
        `when`(commentRepository.findById(any(UUID::class.java))).thenThrow(CommentNotFoundException())

        assertThrows<CommentNotFoundException> { commentService.updateComment(mockCommentUpdatingRequest, UUID.randomUUID().toString()) }
    }

    @Test
    fun `Update comment, should throw error when failed to save entity`() {
        `when`(commentRepository.findById(any(UUID::class.java))).thenReturn(mockOptionalComment)
        `when`(commentRepository.save(any(Comment::class.java))).thenThrow(IllegalArgumentException())

        assertThrows<CommentUpdatingFailedException> { commentService.updateComment(mockCommentUpdatingRequest, UUID.randomUUID().toString()) }
    }

    @Test
    fun `Get comment, should return a comment by id`() {
        `when`(commentRepository.findById(any(UUID::class.java))).thenReturn(mockOptionalComment)

        val result = commentService.getComment(UUID.randomUUID().toString())

        assertThat(result.id).isEqualTo(mockComment.id.toString())
    }

    @Test
    fun `Get comment, should throw error when failed to find entity`() {
        `when`(commentRepository.findById(any(UUID::class.java))).thenThrow(CommentNotFoundException())

        assertThrows<CommentNotFoundException> { commentService.getComment(UUID.randomUUID().toString()) }
    }

    @Test
    fun `Delete comment, should throw error when failed to find entity`() {
        `when`(commentRepository.findById(any(UUID::class.java))).thenThrow(CommentNotFoundException())

        assertThrows<CommentNotFoundException> { commentService.deleteComment(UUID.randomUUID().toString()) }
    }

    @Test
    fun `Delete comment, should throw error when failed to delete entity`() {
        `when`(commentRepository.findById(any(UUID::class.java))).thenReturn(mockOptionalComment)
        `when`(commentRepository.delete(any(Comment::class.java))).thenThrow(IllegalArgumentException())

        assertThrows<CommentDeletingFailedException> { commentService.deleteComment(UUID.randomUUID().toString()) }
    }
}