package com.ongdev.blog.api.models.dtos.requests

import java.util.*


class CommentCreationRequest(
        var userId: UUID,
        val content: String,
        val articleId: UUID,
        val parentId: UUID?
)
