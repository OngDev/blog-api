package com.ongdev.blog.api.services.impl

import com.ongdev.blog.api.exceptions.ArticleCreationFailedException
import com.ongdev.blog.api.exceptions.ArticleDeletingFailedException
import com.ongdev.blog.api.exceptions.ArticleNotFoundException
import com.ongdev.blog.api.exceptions.ArticleUpdatingFailedException
import com.ongdev.blog.api.models.dtos.requests.article.ArticleCreationRequest
import com.ongdev.blog.api.models.dtos.requests.article.ArticleUpdatingRequest
import com.ongdev.blog.api.models.dtos.responses.ArticleUpdatingResponse
import com.ongdev.blog.api.models.dtos.responses.article.ArticleCreationResponse
import com.ongdev.blog.api.models.dtos.responses.article.ArticleListWithPaginationResponse
import com.ongdev.blog.api.models.mapToArticle
import com.ongdev.blog.api.models.repositories.ArticleRepository
import com.ongdev.blog.api.models.repositories.CategoryRepository
import com.ongdev.blog.api.models.toArticleCreationResponse
import com.ongdev.blog.api.models.toArticleEntity
import com.ongdev.blog.api.models.toArticleUpdatingResponse
import com.ongdev.blog.api.services.interfaces.ArticleService
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.*

@Service
class ArticleServiceImpl(val articleRepository: ArticleRepository
                         , val categoryRepository: CategoryRepository) : ArticleService {
    override fun createArticle(articleCreationRequest: ArticleCreationRequest): ArticleCreationResponse {
        val article = articleCreationRequest.toArticleEntity()
//        val optionalAuthor = authorRepository.findById(UUID.fromString(articleCreationRequest.authorId))
//        if(!optionalAuthor.isPresent){
//          throw AuthorNotFoundException()
//        }
//        article.author = optionalAuthor.get()
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

    override fun getListOfArticlesForEachCategory(name: String, pageable: Pageable): ArticleListWithPaginationResponse {
        val categories = categoryRepository.findAllByName(name)
        val articles = articleRepository.findAllByCategories(categories, pageable)
        val articleListResponseContent = articles.map {
            it.toArticleCreationResponse()
        }
        return ArticleListWithPaginationResponse(articleListResponseContent)
    }
}