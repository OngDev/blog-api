package com.ongdev.blog.api.services.impl

import com.ongdev.blog.api.exceptions.*
import com.ongdev.blog.api.models.dtos.requests.ArticleCreationRequest
import com.ongdev.blog.api.models.dtos.requests.ArticleUpdatingRequest
import com.ongdev.blog.api.models.dtos.responses.ArticleCreationResponse
import com.ongdev.blog.api.models.dtos.responses.ArticleListWithPaginationResponse
import com.ongdev.blog.api.models.dtos.responses.ArticleUpdatingResponse
import com.ongdev.blog.api.models.mapToArticle
import com.ongdev.blog.api.models.repositories.ArticleRepository
import com.ongdev.blog.api.models.toArticleCreationResponse
import com.ongdev.blog.api.models.toArticleEntity
import com.ongdev.blog.api.models.toArticleUpdatingResponse
import com.ongdev.blog.api.services.interfaces.ArticleService
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.*

@Service
class ArticleServiceImpl(val articleRepository: ArticleRepository) : ArticleService {

    override fun createArticle(articleCreationRequest: ArticleCreationRequest): ArticleCreationResponse {
        val article = articleCreationRequest.toArticleEntity()
        try {
            return articleRepository.save(article).toArticleCreationResponse()
        } catch (ex: IllegalArgumentException) {
            throw ArticleCreationFailedException()
        }
    }

    override fun getArticlesWithPaginationAndSort(pageable: Pageable): ArticleListWithPaginationResponse {
        val articles = articleRepository.findAll(pageable)
        val articleListResponseContent = articles.map {
            it.toArticleCreationResponse()
        }
        return ArticleListWithPaginationResponse(articleListResponseContent)
    }

    override fun getArticlesByTitleWithPaginationAndSort(title: String, pageable: Pageable): ArticleListWithPaginationResponse {
        val articles = articleRepository.findAllByTitle(title, pageable)
        val articleListResponseContent = articles.map {
            it.toArticleCreationResponse()
        }
        return ArticleListWithPaginationResponse(articleListResponseContent)
    }

    override fun updateArticle(articleUpdatingRequest: ArticleUpdatingRequest, id: String): ArticleUpdatingResponse {
        val optionalArticle = articleRepository.findById(UUID.fromString(id))
        if (!optionalArticle.isPresent) {
            throw ArticleNotFoundException()
        }
        var article = optionalArticle.get()
        article = articleUpdatingRequest.mapToArticle(article)
        try {
            return articleRepository.save(article).toArticleUpdatingResponse()
        } catch (ex: IllegalArgumentException) {
            throw ArticleUpdatingFailedException()
        }
    }

    override fun deleteArticle(id: String) {
        val optionalArticle = articleRepository.findById(UUID.fromString(id))
        if (!optionalArticle.isPresent) {
            throw ArticleNotFoundException()
        }
        val article = optionalArticle.get()
        try {
            return articleRepository.delete(article)
        } catch (ex: IllegalArgumentException) {
            throw ArticleDeletingFailedException()
        }
    }

    override fun getListArticlesByCategory(id: String, pageable: Pageable): ArticleListWithPaginationResponse {
        val articles = articleRepository.findAllArticlesByCategoryId(UUID.fromString(id), pageable).orElseThrow {
            ListArticlesNotFoundException()
        }
        val articleListResponseContent = articles.map {
            it.toArticleCreationResponse()
        }
        return ArticleListWithPaginationResponse(articleListResponseContent)
    }

    override fun getArticleById(id: String): ArticleCreationResponse {
        val article = articleRepository.findById(UUID.fromString(id)).orElseThrow {
            throw ArticleNotFoundException()
        }
        try {
            return article.toArticleCreationResponse()
        } catch (ex: IllegalArgumentException) {
            throw ArticleCreationFailedException()
        }
    }
}