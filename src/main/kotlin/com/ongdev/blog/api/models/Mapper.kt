package com.ongdev.blog.api.models

import com.ongdev.blog.api.models.dtos.requests.Category.CategoryCreationRequest
import com.ongdev.blog.api.models.dtos.requests.article.ArticleCreationRequest
import com.ongdev.blog.api.models.dtos.requests.article.ArticleUpdatingRequest
import com.ongdev.blog.api.models.dtos.responses.article.ArticleCreationResponse
import com.ongdev.blog.api.models.dtos.responses.article.ArticleUpdatingResponse
import com.ongdev.blog.api.models.dtos.responses.category.CategoryCreationResponse
import com.ongdev.blog.api.models.entities.Article
import com.ongdev.blog.api.models.entities.Category

fun ArticleCreationRequest.toArticleEntity() = Article(
        title = title,
        description = description,
        content = content,
        link = link,
        name = name,
        publishDate = publishDate,
        categories = categories
)

fun Article.toArticleCreationResponse() = ArticleCreationResponse(
        id.toString(),
        title = title,
        description = description,
        //authorId = author?.id.toString(),
        name = name,
        content = content,
        link = link,
        publishDate = publishDate,
        categories = categories
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

fun ArticleUpdatingRequest.mapToArticle(article: Article): Article {
    article.content = content
    article.description = description
    article.name = name
    article.title = title
    return article
}

// --------------------------category----------------------------------------
fun Category.toCategoryCreationResponse() = CategoryCreationResponse(
        id.toString(),
        name = name,
        link = link
//        ,articles = articles
)

fun CategoryCreationRequest.toCategoryEntity() = Category(
        link = link,
        name = name
//        ,articles = articles
)