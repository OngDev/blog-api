package com.ongdev.blog.api.services.impl

import com.ongdev.blog.api.exceptions.*
import com.ongdev.blog.api.models.appUtils
import com.ongdev.blog.api.models.dtos.requests.ArticleCreationRequest
import com.ongdev.blog.api.models.dtos.requests.ArticleUpdatingRequest
import com.ongdev.blog.api.models.dtos.responses.ArticleResponse
import com.ongdev.blog.api.models.dtos.responses.ArticlesWithPaginationResponse
import com.ongdev.blog.api.models.repositories.ArticleRepository
import com.ongdev.blog.api.models.toArticleEntity
import com.ongdev.blog.api.models.toArticleResponse
import com.ongdev.blog.api.models.toPageArticleResponse
import com.ongdev.blog.api.services.interfaces.ArticleService
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.*

@Service
class ArticleServiceImpl(val articleRepository: ArticleRepository) : ArticleService {

    override fun createArticle(articleCreationRequest: ArticleCreationRequest): ArticleResponse {
        val linkRequest = appUtils.removeAccent(articleCreationRequest.title)
        if (articleRepository.existsByLink(linkRequest).not()) {
            val article = articleCreationRequest.toArticleEntity()
            article.publishDate = Date()
            try {
                return articleRepository.save(article).toArticleResponse()
            } catch (ex: IllegalArgumentException) {
                throw EntityCreationFailedException("article")
            }
        } else {
            throw EntityIsExistedException("article", "link", linkRequest)
        }
    }

    override fun getArticlesWithPaginationAndSort(pageable: Pageable): ArticlesWithPaginationResponse {
        val articles = articleRepository.findAll(pageable)
        val articleListResponseContent = articles.toPageArticleResponse()
        return ArticlesWithPaginationResponse(articleListResponseContent)
    }

    override fun getArticlesByTitleWithPaginationAndSort(title: String, pageable: Pageable): ArticlesWithPaginationResponse {
        val articles = articleRepository.findAllByTitle(title, pageable)
        val articleListResponseContent = articles.toPageArticleResponse()
        return ArticlesWithPaginationResponse(articleListResponseContent)
    }

    override fun updateArticle(articleUpdatingRequest: ArticleUpdatingRequest, id: String): ArticleResponse {
        var article = articleRepository.findById(UUID.fromString(id)).orElseThrow {
            EntityNotFoundException("article", "id", id)
        }
        val linkRequest = appUtils.removeAccent(articleUpdatingRequest.title)
        if (articleRepository.existsByLink(linkRequest).not() || (linkRequest == article.link)) {
            article = articleUpdatingRequest.toArticleEntity(article)
            try {
                return articleRepository.save(article).toArticleResponse()
            } catch (ex: IllegalArgumentException) {
                throw EntityUpdatingFailedException("article")
            }
        } else {
            throw EntityIsExistedException("article", "link", linkRequest)
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

    override fun getArticlesByCategory(id: String, pageable: Pageable): ArticlesWithPaginationResponse {
        val articles = articleRepository.findAllArticlesByCategoryId(UUID.fromString(id), pageable)
        val articleListResponseContent = articles.toPageArticleResponse()
        return ArticlesWithPaginationResponse(articleListResponseContent)
    }

    override fun getArticleById(id: String): ArticleResponse {
        val article = articleRepository.findById(UUID.fromString(id)).orElseThrow {
            throw EntityNotFoundException("article", "id", id)
        }
        try {
            return article.toArticleResponse()
        } catch (ex: IllegalArgumentException) {
            throw EntityCreationFailedException("article")
        }
    }

    override fun getArticlesByTagId(id: String, pageable: Pageable): ArticlesWithPaginationResponse {
        val articles = articleRepository.findAllArticlesByTagId(UUID.fromString(id), pageable)
        val articleListResponseContent = articles.toPageArticleResponse()
        return ArticlesWithPaginationResponse(articleListResponseContent)
    }

    override fun getArticlesByTagLink(link: String, pageable: Pageable): ArticlesWithPaginationResponse {
        val articles = articleRepository.findAllArticlesByTagLink(link, pageable)
        val articleListResponseContent = articles.toPageArticleResponse()
        return ArticlesWithPaginationResponse(articleListResponseContent)
    }

    override fun getArticlesByLink(link: String, pageable: Pageable): ArticleListWithPaginationResponse {
        val articles = articleRepository.findAllByLink(link, pageable)
        val pageArticleResponse = articles.toPageArticleResponse()
        return ArticleListWithPaginationResponse(pageArticleResponse)
    }
}