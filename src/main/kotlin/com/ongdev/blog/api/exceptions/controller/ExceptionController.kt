package com.ongdev.blog.api.exceptions.controller

import com.ongdev.blog.api.exceptions.*
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ExceptionController {
    @ExceptionHandler(value = [AuthorNotFoundException::class])
    fun handleAuthorNotFoundException(authorNotFoundException: AuthorNotFoundException): ResponseEntity<Any> {
        return ResponseEntity(
                "Could not find author with id: ${authorNotFoundException.message}",
                HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(value = [ArticleCreationFailedException::class])
    fun handleArticleCreationFailedException(articleCreationFailedException: ArticleCreationFailedException)
            : ResponseEntity<Any> {
        return ResponseEntity(
                "Could not create article",
                HttpStatus.BAD_REQUEST
        )
    }

    @ExceptionHandler(value = [ArticleNotFoundException::class])
    fun handleArticleNotFoundException(articleNotFoundException: ArticleNotFoundException): ResponseEntity<Any> {
        return ResponseEntity(
                "Could not find article with id",
                HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(value = [ArticleUpdatingFailedException::class])
    fun handleArticleUpdatingFailedException(articleUpdatingFailedException: ArticleUpdatingFailedException)
            : ResponseEntity<Any> {
        return ResponseEntity(
                "Could not update article",
                HttpStatus.BAD_REQUEST
        )
    }

    @ExceptionHandler(value = [ArticleDeletingFailedException::class])
    fun handleArticleDeletingFailedException(articleDeletingFailedException: ArticleDeletingFailedException)
            : ResponseEntity<Any> {
        return ResponseEntity(
                "Could not delete article",
                HttpStatus.BAD_REQUEST
        )
    }

    @ExceptionHandler(value = [CategoryCreationFailedException::class])
    fun handleCategoryCreationFailedException(categoryCreationFailedException: CategoryCreationFailedException)
            : ResponseEntity<Any> {
        return ResponseEntity(
                "Could not create category",
                HttpStatus.BAD_REQUEST
        )
    }

    @ExceptionHandler(value = [CategoryNotFoundException::class])
    fun handleCategoryNotFoundException(categoryNotFoundException: CategoryNotFoundException): ResponseEntity<Any> {
        return ResponseEntity(
                "Could not find category with id",
                HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(value = [CategoryUpdatingFailedException::class])
    fun handleCategoryUpdatingFailedException(categoryUpdatingFailedException: CategoryUpdatingFailedException)
            : ResponseEntity<Any> {
        return ResponseEntity(
                "Could not update category",
                HttpStatus.BAD_REQUEST
        )
    }

    @ExceptionHandler(value = [CategoryDeletingFailedException::class])
    fun handleCategoryDeletingFailedException(categoryDeletingFailedException: CategoryDeletingFailedException)
            : ResponseEntity<Any> {
        return ResponseEntity(
                "Could not delete category",
                HttpStatus.BAD_REQUEST
        )
    }
}