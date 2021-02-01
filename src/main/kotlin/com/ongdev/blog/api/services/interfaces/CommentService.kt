package com.ongdev.blog.api.services.interfaces

import com.ongdev.blog.api.models.dtos.requests.CommentCreationRequest
import com.ongdev.blog.api.models.dtos.requests.CommentUpdatingRequest
import com.ongdev.blog.api.models.dtos.responses.CommentResponse
import com.ongdev.blog.api.models.dtos.responses.CommentsWithPaginationResponse
import org.springframework.data.domain.Pageable

interface CommentService {
    fun createComment(commentCreationRequest: CommentCreationRequest): CommentResponse
    fun updateCommentById(commentId: String, CommentUpdatingRequest: CommentUpdatingRequest): CommentResponse
    fun deleteCommentById(commentId: String)
    fun getCommentById(commentId: String): CommentResponse
    fun getCommentsByArticle(articleId: String, pageable: Pageable): CommentsWithPaginationResponse
}