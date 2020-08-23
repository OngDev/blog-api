package com.ongdev.blog.api.models.dtos.requests.article

import java.util.*

class ArticleUpdatingRequest(
        val title: String,
        val description: String,
        val content: String,
        val name: String
)
