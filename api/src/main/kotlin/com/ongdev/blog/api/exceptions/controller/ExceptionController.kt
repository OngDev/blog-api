package com.ongdev.blog.api.exceptions.controller

import com.ongdev.blog.api.exceptions.ArticleCreationFailedException
import com.ongdev.blog.api.exceptions.AuthorNotFoundException
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
}