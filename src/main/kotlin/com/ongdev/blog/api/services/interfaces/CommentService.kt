package com.ongdev.blog.api.services.interfaces

import com.ongdev.blog.api.models.dtos.requests.CommentCreationRequest
import com.ongdev.blog.api.models.dtos.requests.CommentUpdatingRequest
import com.ongdev.blog.api.models.dtos.responses.CommentCreationResponse
import com.ongdev.blog.api.models.dtos.responses.CommentUpdatingResponse

interface CommentService {
    fun createComment(commentCreationRequest: CommentCreationRequest): CommentCreationResponse
    fun updateComment(CommentUpdatingRequest: CommentUpdatingRequest, id: String): CommentUpdatingResponse
    fun deleteComment(id: String)
    fun getComment(id: String): CommentCreationResponse
}