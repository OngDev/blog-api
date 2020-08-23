package com.ongdev.blog.api.models.dtos.requests

import java.util.*

class ArticleCreationRequest(
        val title: String,
        val description: String,
        val content: String,
        val name: String,
        val publishDate: Date?,
        val link: String
)
