package com.ongdev.blog.api.models

import com.ongdev.blog.api.models.dtos.ArticleDTO
import com.ongdev.blog.api.models.entities.Article

fun ArticleDTO.toArticleEntity() = Article(
        title = title,
        description = description
)


fun Article.toArticleDTO() = ArticleDTO(
        id.toString(),
        title,
        description
)
