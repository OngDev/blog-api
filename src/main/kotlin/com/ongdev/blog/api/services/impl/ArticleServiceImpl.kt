package com.ongdev.blog.api.services.impl

import com.ongdev.blog.api.exceptions.*
import com.ongdev.blog.api.models.dtos.requests.article.ArticleCreationRequest
import com.ongdev.blog.api.models.dtos.requests.article.ArticleUpdatingRequest
import com.ongdev.blog.api.models.dtos.responses.article.ArticleCreationResponse
import com.ongdev.blog.api.models.dtos.responses.article.ArticleListWithPaginationResponse
import com.ongdev.blog.api.models.dtos.responses.article.ArticleUpdatingResponse
import com.ongdev.blog.api.models.mapToArticle
import com.ongdev.blog.api.models.repositories.ArticleRepository
import com.ongdev.blog.api.models.repositories.CategoryRepository
import com.ongdev.blog.api.models.toArticleCreationResponse
import com.ongdev.blog.api.models.toArticleEntity
import com.ongdev.blog.api.models.toArticleUpdatingResponse
import com.ongdev.blog.api.services.interfaces.ArticleService
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.*

@Service
class ArticleServiceImpl(val articleRepository: ArticleRepository
                         , val categoryRepository: CategoryRepository) : ArticleService {
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

    override fun getListOfArticlesForEachCategory(name: String, currentPage:Int): ArticleListWithPaginationResponse {
        val categories = categoryRepository.findAllByName(name)
        var getCurrentPage:Int=0
        if(currentPage-1>getCurrentPage)
            getCurrentPage=currentPage-1
        val articles = articleRepository.findAllByCategoriesIn(categories, PageRequest.of(getCurrentPage, 10))
        if(articles.isEmpty)
            throw IsEmptyException()
        else{
            val articleListResponseContent = articles.map {
                it.toArticleCreationResponse()
            }
            return ArticleListWithPaginationResponse(articleListResponseContent)
        }
    }
}


