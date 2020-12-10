package com.ongdev.blog.api.services.impl

import com.ongdev.blog.api.exceptions.EntityCreationFailedException
import com.ongdev.blog.api.exceptions.EntityDeletingFailedException
import com.ongdev.blog.api.exceptions.EntityNotFoundException
import com.ongdev.blog.api.exceptions.EntityUpdatingFailedException
import com.ongdev.blog.api.models.*
import com.ongdev.blog.api.models.dtos.requests.CommentCreationRequest
import com.ongdev.blog.api.models.dtos.requests.CommentUpdatingRequest
import com.ongdev.blog.api.models.dtos.responses.CommentCreationResponse
import com.ongdev.blog.api.models.dtos.responses.CommentUpdatingResponse
import com.ongdev.blog.api.models.repositories.CommentRepository
import com.ongdev.blog.api.services.interfaces.CommentService
import org.springframework.stereotype.Service
import java.util.*

@Service
class CommentServiceImpl(val commentRepository: CommentRepository) : CommentService {

    override fun createComment(commentCreationRequest: CommentCreationRequest): CommentCreationResponse {
        val comment = commentCreationRequest.toCommentEntity()
        try {
            return commentRepository.save(comment).toCommentCreationResponse(emptyList())
        } catch (ex: IllegalArgumentException) {
            throw EntityCreationFailedException("comment")
        }
    }

    override fun updateComment(CommentUpdatingRequest: CommentUpdatingRequest, id: String): CommentUpdatingResponse {
        val comment = commentRepository.findById(UUID.fromString(id)).orElseThrow {
            throw EntityNotFoundException("comment", "id", id)
        }
        val updateComment = CommentUpdatingRequest.toComment(comment)
        val children = updateComment.children?.toChildrenResponse()
        try {
            return commentRepository.save(updateComment).toCommentUpdatingResponse(children)
        } catch (ex: IllegalArgumentException) {
            throw EntityUpdatingFailedException("comment")
        }
    }

    override fun deleteComment(id: String) {
        val comment = commentRepository.findById(UUID.fromString(id)).orElseThrow {
            throw EntityNotFoundException("comment", "id", id)
        }
        try {
            return commentRepository.delete(comment)
        } catch (ex: IllegalArgumentException) {
            throw EntityDeletingFailedException("comment")
        }
    }

    override fun getComment(id: String): CommentCreationResponse {
        val comment = commentRepository.findById(UUID.fromString(id)).orElseThrow {
            throw EntityNotFoundException("comment", "id", id)
        }
        val children = comment.children?.toChildrenResponse()
        return comment.toCommentCreationResponse(children)
    }
}