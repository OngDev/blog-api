package com.ongdev.blog.api.models.dtos.responses

import java.util.*

class ArticleUpdatingResponses(
        val id: String,
        val title: String,
        val description: String,
        val content: String,
        val name: String,
        val publishDate: Date?,
        val link: String
        //val authorId: String
)
