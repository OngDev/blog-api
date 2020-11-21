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

	@ExceptionHandler(value = [EntityCreationFailedException::class])
	fun handleCreationFailedException(exception: EntityCreationFailedException) : ResponseEntity<Any> {
		return ResponseEntity(
				"Could not create " + exception.entityName,
				HttpStatus.BAD_REQUEST)
	}

	@ExceptionHandler(value = [EntityDeletingFailedException::class])
	fun handleEntityDeletingFailedException(exception: EntityDeletingFailedException) : ResponseEntity<Any> {
		return ResponseEntity(
				"Could not delete " + exception.entityName,
				HttpStatus.BAD_REQUEST)
	}

	@ExceptionHandler(value = [EntityUpdatingFailedException::class])
	fun handleEntityUpdatingFailedException(exception: EntityUpdatingFailedException) : ResponseEntity<Any> {
		return ResponseEntity(
				"Could not update " + exception.entityName,
				HttpStatus.BAD_REQUEST)
	}

	@ExceptionHandler(value = [EntityNotFoundException::class])
	fun handleEntityNotFoundException(exception: EntityNotFoundException) : ResponseEntity<Any> {
		return ResponseEntity(
				"Could not find ${exception.entityName} with ${exception.key}: ${exception.value}",
				HttpStatus.NOT_FOUND)
	}

	@ExceptionHandler(value= [ArticlesNotFoundException::class])
	fun handleArticlesNotFoundException(articlesNotFoundExceptionException: ArticlesNotFoundException)
			: ResponseEntity<Any> {
		return ResponseEntity(
				"Could not find articles",
				HttpStatus.BAD_REQUEST
		)
	}
}