package com.ongdev.blog.api.models

import com.ongdev.blog.api.models.dtos.requests.ArticleCreationRequest
import com.ongdev.blog.api.models.dtos.responses.ArticleCreationResponse
import com.ongdev.blog.api.models.entities.Article

fun ArticleCreationRequest.toArticleEntity() = Article(
        title = title,
        description = description
)


fun Article.toArticleCreationResponse() = ArticleCreationResponse(
	id.toString(),
	title = title,
	description = description,
	authorId = author?.id.toString()
)
