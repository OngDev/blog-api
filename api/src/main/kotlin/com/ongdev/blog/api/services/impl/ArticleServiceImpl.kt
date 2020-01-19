package com.ongdev.blog.api.services.impl

import com.ongdev.blog.api.exceptions.ArticleCreationFailedException
import com.ongdev.blog.api.exceptions.AuthorNotFoundException
import com.ongdev.blog.api.models.dtos.requests.ArticleCreationRequest
import com.ongdev.blog.api.models.dtos.responses.ArticleCreationResponse
import com.ongdev.blog.api.models.repositories.ArticleRepository
import com.ongdev.blog.api.models.repositories.AuthorRepository
import com.ongdev.blog.api.models.toArticleCreationResponse
import com.ongdev.blog.api.models.toArticleEntity
import com.ongdev.blog.api.services.interfaces.ArticleService
import org.springframework.stereotype.Service
import java.util.*

@Service
class ArticleServiceImpl(val articleRepository: ArticleRepository, val authorRepository: AuthorRepository) : ArticleService {
    override fun createArticle(articleCreationRequest: ArticleCreationRequest): ArticleCreationResponse {
        val article = articleCreationRequest.toArticleEntity()
        val optionalAuthor = authorRepository.findById(UUID.fromString(articleCreationRequest.authorId))
        if(!optionalAuthor.isPresent){
          throw AuthorNotFoundException()
        }
        article.author = optionalAuthor.get()
        try {
            return articleRepository.save(article).toArticleCreationResponse()
        }catch (ex: IllegalArgumentException){
            throw ArticleCreationFailedException()
        }
    }
}