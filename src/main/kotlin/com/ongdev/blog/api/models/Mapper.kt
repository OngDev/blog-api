package com.ongdev.blog.api.models

import com.ongdev.blog.api.models.dtos.requests.ArticleCreationRequest
import com.ongdev.blog.api.models.dtos.requests.ArticleUpdatingRequest
import com.ongdev.blog.api.models.dtos.requests.TagRequest
import com.ongdev.blog.api.models.dtos.requests.*
import com.ongdev.blog.api.models.dtos.responses.ArticleCreationResponse
import com.ongdev.blog.api.models.dtos.responses.ArticleUpdatingResponse
import com.ongdev.blog.api.models.dtos.responses.CategoryCreationResponse
import com.ongdev.blog.api.models.dtos.responses.TagResponse
import com.ongdev.blog.api.models.entities.Article
import com.ongdev.blog.api.models.entities.Category
import com.ongdev.blog.api.models.entities.Tag
import com.ongdev.blog.api.utils.AppUtils
import org.springframework.data.domain.Page

var appUtils: AppUtils = AppUtils()

fun ArticleCreationRequest.toArticleEntity() = Article(
        title = title,
        description = description,
        content = content,
        link = appUtils.removeAccent(title)
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

fun ArticleUpdatingRequest.mapToArticle(article: Article): Article {
    article.content = content
    article.description = description
    article.title = title
    article.link = appUtils.removeAccent(title)
    return article
}

fun Page<Article>.toPageArticleResponse() = map {
    it.toArticleCreationResponse()
}

fun TagRequest.toTag() = Tag(
        name = name,
        link = appUtils.removeAccent(name)
)

fun Tag.toTagResponse() = TagResponse(
        id = id.toString(),
        name = name,
        link = link
)

fun Tag.update(tagRequest: TagRequest) {
    name = tagRequest.name
    link = appUtils.removeAccent(tagRequest.name)
}

fun Page<Tag>.toTagsResponse() = map{
    it.toTagResponse()
}

fun CategoryUpdateRequest.toCategory(category: Category): Category {
    category.name = name
    return category
}

fun Category.toCategoryCreationResponse() = CategoryCreationResponse(
        id.toString(),
        name = name,
        link = appUtils.removeAccent(name)
)

fun CategoryCreationRequest.toCategoryEntity() = Category(
        name = name,
        link = appUtils.removeAccent(name)
)

fun Page<Category>.toPageCategoryResponse() = map {
    it.toCategoryCreationResponse()
}