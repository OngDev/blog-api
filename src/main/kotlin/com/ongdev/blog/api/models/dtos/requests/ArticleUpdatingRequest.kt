package com.ongdev.blog.api.models.dtos.requests

import java.util.*

class ArticleUpdatingRequest(
        val title: String,
        val description: String,
        val content: String
)
