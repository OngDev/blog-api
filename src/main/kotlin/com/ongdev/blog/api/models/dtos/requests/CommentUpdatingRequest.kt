package com.ongdev.blog.api.models.dtos.requests

import java.util.*

class CommentUpdatingRequest(
        val content: String,
        val createAt: Date?
)