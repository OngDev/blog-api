package com.ongdev.blog.api.models.dtos.responses

import java.util.*

class ArticleResponse(
	val id: String,
    val title: String,
    val description: String,
    val content: String,
    val publishDate: Date?,
    val link: String
	//val authorId: String
)
