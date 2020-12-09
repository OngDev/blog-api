package com.ongdev.blog.api.models.dtos.requests

class ArticleUpdatingRequest(
        val title: String,
        val description: String,
        val content: String,
        val link: String
)
