package com.ongdev.blog.api.models.dtos.responses

import java.util.*

class CommentResponse(
        val id: String,
        val userId: String,
        val content: String,
        val createAt: Date?,
        val children: Set<ChildResponse>?
)