package com.ongdev.blog.api.exceptions.controller

import com.ongdev.blog.api.exceptions.*
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ExceptionController {
	@ExceptionHandler(value = [AuthorNotFoundException::class])
	fun handleAuthorNotFoundException(authorNotFoundException: AuthorNotFoundException) : ResponseEntity<Any> {
		return ResponseEntity(
			"Could not find author with id: ${authorNotFoundException.message}",
		    HttpStatus.NOT_FOUND)
	}

	@ExceptionHandler(value= [ArticleCreationFailedException::class])
	fun handleArticleCreationFailedException(articleCreationFailedException: ArticleCreationFailedException)
		: ResponseEntity<Any> {
		return ResponseEntity(
			"Could not create article",
			HttpStatus.BAD_REQUEST
		)
	}

	@ExceptionHandler(value = [ArticleNotFoundException::class])
	fun handleArticleNotFoundException(articleNotFoundException: ArticleNotFoundException) : ResponseEntity<Any> {
		return ResponseEntity(
				"Could not find article with id",
				HttpStatus.NOT_FOUND)
	}

	@ExceptionHandler(value= [ArticleDeletingFailedException::class])
	fun handleArticleDeletingFailedException(articleDeletingFailedException: ArticleDeletingFailedException)
			: ResponseEntity<Any> {
		return ResponseEntity(
				"Could not delete article",
				HttpStatus.BAD_REQUEST
		)
	}

	@ExceptionHandler(value = [IsEmptyException::class])
	fun handleisAnEmptyException(isEmptyException: IsEmptyException): ResponseEntity<Any> {
		return ResponseEntity(
				"is Empty",
				HttpStatus.NOT_FOUND)
	}
}