package com.ongdev.blog.api.services.impl

import com.ongdev.blog.api.exceptions.EntityCreationFailedException
import com.ongdev.blog.api.exceptions.EntityDeletingFailedException
import com.ongdev.blog.api.exceptions.EntityNotFoundException
import com.ongdev.blog.api.exceptions.EntityUpdatingFailedException
import com.ongdev.blog.api.models.*
import com.ongdev.blog.api.models.dtos.requests.CommentCreationRequest
import com.ongdev.blog.api.models.dtos.requests.CommentUpdatingRequest
import com.ongdev.blog.api.models.dtos.responses.CommentResponse
import com.ongdev.blog.api.models.dtos.responses.CommentsWithPaginationResponse
import com.ongdev.blog.api.models.repositories.ArticleRepository
import com.ongdev.blog.api.models.repositories.CommentRepository
import com.ongdev.blog.api.services.interfaces.CommentService
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.*

@Service
class CommentServiceImpl(val commentRepository: CommentRepository
                         , val articleRepository: ArticleRepository) : CommentService {

    override fun createComment(commentCreationRequest: CommentCreationRequest): CommentResponse {
        val article = articleRepository.findById(commentCreationRequest.articleId).orElseThrow {
            throw EntityNotFoundException("article", "id", commentCreationRequest.articleId.toString())
        }
        val comment = commentCreationRequest.toCommentEntity()
        comment.article = article
        comment.createAt = Date()
        comment.parent = null
        val parentId = commentCreationRequest.parentId
        if (parentId != null) {
            val optionalParent = commentRepository.findById(UUID.fromString(parentId.toString()))
            if (optionalParent.isPresent) {
                val objHasParent = optionalParent.get()
                if (objHasParent.parent != null) {
                    comment.parent = objHasParent.parent
                } else {
                    comment.parent = objHasParent
                }
            }
        }
        try {
            return commentRepository.save(comment).toCommentCreationResponse(emptyList())
        } catch (ex: IllegalArgumentException) {
            throw EntityCreationFailedException("comment")
        }
    }

    override fun updateCommentById(commentId: String, CommentUpdatingRequest: CommentUpdatingRequest): CommentResponse {
        val comment = commentRepository.findById(UUID.fromString(commentId)).orElseThrow {
            throw EntityNotFoundException("comment", "id", commentId)
        }
        val updateComment = CommentUpdatingRequest.toCommentEntity(comment)
        val children = updateComment.children?.toChildrenResponse()
        try {
            updateComment.createAt = Date()
            return commentRepository.save(updateComment).toCommentUpdatingResponse(children)
        } catch (ex: IllegalArgumentException) {
            throw EntityUpdatingFailedException("comment")
        }
    }

    override fun deleteCommentById(commentId: String) {
        val comment = commentRepository.findById(UUID.fromString(commentId)).orElseThrow {
            throw EntityNotFoundException("comment", "id", commentId)
        }
        try {
            return commentRepository.delete(comment)
        } catch (ex: IllegalArgumentException) {
            throw EntityDeletingFailedException("comment")
        }
    }

    override fun getCommentById(commentId: String): CommentResponse {
        val comment = commentRepository.findById(UUID.fromString(commentId)).orElseThrow {
            throw EntityNotFoundException("comment", "id", commentId)
        }
        val children = comment.children?.toChildrenResponse()
        return comment.toCommentCreationResponse(children)
    }

    override fun getCommentsByArticle(articleId: String, pageable: Pageable): CommentsWithPaginationResponse {
        val article = articleRepository.findById(UUID.fromString(articleId)).orElseThrow {
            throw EntityNotFoundException("article", "id", articleId)
        }
        val comments = commentRepository.findAllByArticle(article, pageable)
        val pageCommentsResponse = comments.toPageCommentResponse()
        return CommentsWithPaginationResponse(pageCommentsResponse)
    }
}