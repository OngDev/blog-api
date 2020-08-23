package com.ongdev.blog.api.models.dtos.requests.article

import com.ongdev.blog.api.models.entities.Category
import java.util.*

class ArticleCreationRequest(
        val title: String,
        val description: String,
        val content: String,
        val publishDate: Date?,
        val link: String,
        val categories: Set<Category>
)
