package com.ongdev.blog.api.services.interfaces

import com.ongdev.blog.api.models.dtos.requests.CommentCreationRequest
import com.ongdev.blog.api.models.dtos.requests.CommentUpdatingRequest
import com.ongdev.blog.api.models.dtos.responses.CommentResponse

interface CommentService {
    fun createComment(commentCreationRequest: CommentCreationRequest): CommentResponse
    fun updateComment(CommentUpdatingRequest: CommentUpdatingRequest, id: String): CommentResponse
    fun deleteComment(id: String)
    fun getComment(id: String): CommentResponse
}