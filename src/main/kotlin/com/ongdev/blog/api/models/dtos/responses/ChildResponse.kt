package com.ongdev.blog.api.models.dtos.responses

import java.util.*

class ChildResponse (
        val commentId: String,
        val userId: String,
        val content: String,
        val createAt: Date?
)