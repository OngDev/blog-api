package com.ongdev.blog.api.models

import com.ongdev.blog.api.models.dtos.requests.ArticleCreationRequest
import com.ongdev.blog.api.models.dtos.requests.ArticleUpdatingRequest
import com.ongdev.blog.api.models.dtos.responses.ArticleCreationResponse
import com.ongdev.blog.api.models.dtos.responses.ArticleUpdatingResponse
import com.ongdev.blog.api.models.entities.Article

fun ArticleCreationRequest.toArticleEntity() = Article(
        title = title,
        description = description,
        content = content,
        link = link,
        name = name,
        publishDate = publishDate
)

fun Article.toArticleCreationResponse() = ArticleCreationResponse(
        id.toString(),
        title = title,
        description = description,
        //authorId = author?.id.toString(),
        name = name,
        content = content,
        link = link,
        publishDate = publishDate
)

fun Article.toArticleUpdatingResponse() = ArticleUpdatingResponse(
        id.toString(),
        title = title,
        description = description,
        //authorId = author?.id.toString(),
        name = name,
        content = content,
        link = link,
        publishDate = publishDate
)

fun ArticleUpdatingRequest.mapToArticle(article: Article) : Article {
    article.content = content
    article.description = description
    article.name = name
    article.title = title
    return article
}