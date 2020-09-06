package com.ongdev.blog.api.models.dtos.responses

import java.util.*

class CommentUpdatingResponse(
        val id: String,
        val userId: String,
        val content: String,
        val createAt: Date?,
        val children: Set<ChildCreationResponse>?
)