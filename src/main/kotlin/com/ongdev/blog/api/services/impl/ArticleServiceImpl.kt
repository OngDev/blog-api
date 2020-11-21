package com.ongdev.blog.api.services.impl

import com.ongdev.blog.api.exceptions.ArticleCreationFailedException
import com.ongdev.blog.api.exceptions.ArticleDeletingFailedException
import com.ongdev.blog.api.exceptions.ArticleNotFoundException
import com.ongdev.blog.api.exceptions.ArticleUpdatingFailedException
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
import org.springframework.data.domain.PageRequest
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

    override fun getArticlesWithPagination(page: Int): ArticleListWithPaginationResponse {
        val articles = articleRepository.findAll(PageRequest.of(page, 10))
        val articleListResponseContent = articles.map {
            it.toArticleCreationResponse()
        }
        return ArticleListWithPaginationResponse(articleListResponseContent)
    }

    override fun updateArticle(articleUpdatingRequest: ArticleUpdatingRequest, id: String): ArticleUpdatingResponse {
        var article = articleRepository.findById(UUID.fromString(id)).orElseThrow {
            ArticleNotFoundException()
        }
        article = articleUpdatingRequest.mapToArticle(article)
        try {
            return articleRepository.save(article).toArticleUpdatingResponse()
        } catch (ex: IllegalArgumentException) {
            throw ArticleUpdatingFailedException()
        }
    }

    override fun deleteArticle(id: String) {
        val article = articleRepository.findById(UUID.fromString(id)).orElseThrow {
            throw ArticleNotFoundException()
        }
        try {
            return articleRepository.delete(article)
        } catch (ex: IllegalArgumentException) {
            throw ArticleDeletingFailedException()
        }
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