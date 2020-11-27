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
        val optionalArticleByLink = articleRepository.findByLink(articleCreationRequest.link)
        if (!optionalArticleByLink.isPresent) {
            val article = articleCreationRequest.toArticleEntity()
            article.publishDate = Date()
            try {
                return articleRepository.save(article).toArticleCreationResponse()
            } catch (ex: IllegalArgumentException) {
                throw EntityCreationFailedException("article")
            }
        } else {
            throw LinkIsExistedException("article", "link", articleCreationRequest.link)
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
        val optionalArticleByLink = articleRepository.findByLink(articleUpdatingRequest.link)
        if (!optionalArticleByLink.isPresent) {
            var article = articleRepository.findById(UUID.fromString(id)).orElseThrow {
                EntityNotFoundException("article", "id", id)
            }
            article = articleUpdatingRequest.mapToArticle(article)
            try {
                return articleRepository.save(article).toArticleUpdatingResponse()
            } catch (ex: IllegalArgumentException) {
                throw EntityUpdatingFailedException("article")
            }
        } else {
            throw LinkIsExistedException("article", "link", articleUpdatingRequest.link)
        }
    }

    override fun deleteArticle(id: String) {
        val article = articleRepository.findById(UUID.fromString(id)).orElseThrow {
            throw EntityNotFoundException("article", "id", id)
        }
        try {
            return articleRepository.delete(article)
        } catch (ex: IllegalArgumentException) {
            throw EntityDeletingFailedException("article")
        }
    }

    override fun getArticleById(id: String): ArticleCreationResponse {
        val article = articleRepository.findById(UUID.fromString(id)).orElseThrow {
            throw EntityNotFoundException("article", "id", id)
        }
        try {
            return article.toArticleCreationResponse()
        } catch (ex: IllegalArgumentException) {
            throw EntityCreationFailedException("article")
        }
    }
}