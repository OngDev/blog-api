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
        val optionalComment = commentRepository.findById(UUID.fromString(id)).orElseThrow {
            throw EntityNotFoundException("comment", "id", id)
        }
        val comment = CommentUpdatingRequest.toComment(optionalComment)
        val children = comment.children?.map {
            it.toChildCreationResponse()
        }
        try {
            return commentRepository.save(comment).toCommentUpdatingResponse(children)
        } catch (ex: IllegalArgumentException) {
            throw EntityUpdatingFailedException("comment")
        }
    }

    override fun deleteComment(id: String) {
        val optionalComment = commentRepository.findById(UUID.fromString(id)).orElseThrow {
            throw EntityNotFoundException("comment", "id", id)
        }
        try {
            return commentRepository.delete(optionalComment)
        } catch (ex: IllegalArgumentException) {
            throw EntityDeletingFailedException("comment")
        }
    }

    override fun getComment(id: String): CommentCreationResponse {
        val optionalComment = commentRepository.findById(UUID.fromString(id)).orElseThrow {
            throw EntityNotFoundException("comment", "id", id)
        }
        val children = optionalComment.children?.map {
            it.toChildCreationResponse()
        }
        return optionalComment.toCommentCreationResponse(children)
    }

}