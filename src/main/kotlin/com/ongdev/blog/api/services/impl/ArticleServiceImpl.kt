package com.ongdev.blog.api.services.impl

import com.ongdev.blog.api.exceptions.*
import com.ongdev.blog.api.models.*
import com.ongdev.blog.api.models.dtos.requests.ArticleCreationRequest
import com.ongdev.blog.api.models.dtos.requests.ArticleUpdatingRequest
import com.ongdev.blog.api.models.dtos.responses.ArticleCreationResponse
import com.ongdev.blog.api.models.dtos.responses.ArticleListWithPaginationResponse
import com.ongdev.blog.api.models.dtos.responses.ArticleUpdatingResponse
import com.ongdev.blog.api.models.entities.Article
import com.ongdev.blog.api.models.entities.Category
import com.ongdev.blog.api.models.repositories.ArticleRepository
import com.ongdev.blog.api.models.repositories.CategoryRepository
import com.ongdev.blog.api.services.interfaces.ArticleService
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.*
import kotlin.collections.HashSet

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
        val listCategory = HashSet<Category>()
        listCategory.add(Category("c1", "c1", emptySet()))
        listCategory.add(Category("c2", "c2", emptySet()))
        listCategory.add(Category("c3", "c3", emptySet()))
        categoryRepository.saveAll(listCategory)
        val listArticle = ArrayList<Article>()
        listArticle.add(0, Article("title", "", "", "", null, emptySet(), listCategory, emptySet()))
        listArticle.add(1, Article("2", "", "", "", null, emptySet(), emptySet(), emptySet()))
        listArticle.add(2, Article("3", "", "", "", null, emptySet(), emptySet(), emptySet()))
        listArticle.add(3, Article("4", "", "", "", null, emptySet(), emptySet(), emptySet()))
        articleRepository.saveAll(listArticle)
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

    override fun getListOfArticlesByCategory(id: String, pageable: Pageable): ArticleListWithPaginationResponse {
        val articles = articleRepository.findAllArticlesByCategoryId(UUID.fromString(id), pageable)
        if(articles.isEmpty)
            throw ListOfArticlesIsEmptyException()
        val articleListResponseContent = articles.map {
            it.toArticleCreationResponse()
        }
        return ArticleListWithPaginationResponse(articleListResponseContent)
    }
}