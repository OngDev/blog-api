package com.ongdev.blog.api.models

import com.ongdev.blog.api.models.dtos.requests.ArticleCreationRequest
import com.ongdev.blog.api.models.dtos.requests.ArticleUpdatingRequest
import com.ongdev.blog.api.models.dtos.requests.CategoryCreationRequest
import com.ongdev.blog.api.models.dtos.requests.CategoryUpdateRequest
import com.ongdev.blog.api.models.dtos.responses.ArticleCreationResponse
import com.ongdev.blog.api.models.dtos.responses.ArticleUpdatingResponse
import com.ongdev.blog.api.models.dtos.responses.CategoryCreationResponse
import com.ongdev.blog.api.models.entities.Article
import com.ongdev.blog.api.models.entities.Category

fun ArticleCreationRequest.toArticleEntity() = Article(
        title = title,
        description = description,
        content = content,
        link = link,
        publishDate = publishDate
)

fun Article.toArticleCreationResponse() = ArticleCreationResponse(
        id.toString(),
        title = title,
        description = description,
        content = content,
        link = link,
        publishDate = publishDate
)


fun Article.toArticleUpdatingResponse() = ArticleUpdatingResponse(
        id.toString(),
        title = title,
        description = description,
        content = content,
        link = link,
        publishDate = publishDate
)

fun ArticleUpdatingRequest.mapToArticle(article: Article) : Article {
    article.content = content
    article.description = description
    article.title = title
    return article
}

fun CategoryUpdateRequest.mapToCategory(category: Category) : Category {
    category.name = name
    return category
}

fun Category.toCategoryCreationResponse() =  CategoryCreationResponse(
        id.toString(),
        name = name,
        link = link
)

fun CategoryCreationRequest.toCategoryEntity() = Category(
        name = name,
        link = link
)