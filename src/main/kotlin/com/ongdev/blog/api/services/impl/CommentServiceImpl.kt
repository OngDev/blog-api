package com.ongdev.blog.api.services.impl

import com.ongdev.blog.api.exceptions.EntityCreationFailedException
import com.ongdev.blog.api.exceptions.EntityDeletingFailedException
import com.ongdev.blog.api.exceptions.EntityNotFoundException
import com.ongdev.blog.api.exceptions.EntityUpdatingFailedException
import com.ongdev.blog.api.models.*
import com.ongdev.blog.api.models.dtos.requests.CommentCreationRequest
import com.ongdev.blog.api.models.dtos.requests.CommentUpdatingRequest
import com.ongdev.blog.api.models.dtos.responses.CommentResponse
import com.ongdev.blog.api.models.repositories.CommentRepository
import com.ongdev.blog.api.services.interfaces.CommentService
import org.springframework.stereotype.Service
import java.util.*

@Service
class CommentServiceImpl(val commentRepository: CommentRepository) : CommentService {

    override fun createComment(commentCreationRequest: CommentCreationRequest): CommentResponse {
        val comment = commentCreationRequest.toCommentEntity()
        try {
            return commentRepository.save(comment).toCommentResponse(emptyList())
        } catch (ex: IllegalArgumentException) {
            throw EntityCreationFailedException("comment")
        }
    }

    override fun updateComment(CommentUpdatingRequest: CommentUpdatingRequest, id: String): CommentResponse {
        val comment = commentRepository.findById(UUID.fromString(id)).orElseThrow {
            throw EntityNotFoundException("comment", "id", id)
        }
        val updateComment = CommentUpdatingRequest.toCommentEntity(comment)
        val children = updateComment.children?.toChildrenResponse()
        try {
            return commentRepository.save(updateComment).toCommentResponse(children)
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

    override fun getComment(id: String): CommentResponse {
        val comment = commentRepository.findById(UUID.fromString(id)).orElseThrow {
            throw EntityNotFoundException("comment", "id", id)
        }
        val children = comment.children?.toChildrenResponse()
        return comment.toCommentResponse(children)
    }
}