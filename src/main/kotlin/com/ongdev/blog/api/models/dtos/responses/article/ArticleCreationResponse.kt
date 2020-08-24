package com.ongdev.blog.api.models.dtos.responses.article

import com.ongdev.blog.api.models.entities.Category
import java.util.*

class ArticleCreationResponse(
        val id: String,
        val title: String,
        val description: String,
        val content: String,
        val name: String,
        val publishDate: Date?,
        val link: String,
        val nameCategories: List<String>
)

class ArticleUpdatingResponse(
        val id: String,
        val title: String,
        val description: String,
        val content: String,
        val name: String,
        val publishDate: Date?,
        val link: String
)
