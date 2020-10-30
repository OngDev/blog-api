package com.ongdev.blog.api.exceptions.controller

import com.ongdev.blog.api.exceptions.CommentCreationFailedException
import com.ongdev.blog.api.exceptions.CommentDeletingFailedException
import com.ongdev.blog.api.exceptions.CommentNotFoundException
import com.ongdev.blog.api.exceptions.CommentUpdatingFailedException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class CommentExceptionController {
    @ExceptionHandler(value= [CommentCreationFailedException::class])
    fun handleCommentCreationFailedException(commentCreationFailedException: CommentCreationFailedException)
            : ResponseEntity<Any> {
        return ResponseEntity(
                "Could not create comment",
                HttpStatus.BAD_REQUEST
        )
    }

    @ExceptionHandler(value = [CommentNotFoundException::class])
    fun handleCommentNotFoundException(commentNotFoundException: CommentNotFoundException) : ResponseEntity<Any> {
        return ResponseEntity(
                "Could not find comment with id",
                HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(value= [CommentUpdatingFailedException::class])
    fun handleCommentUpdatingFailedException(commentUpdatingFailedException: CommentUpdatingFailedException)
            : ResponseEntity<Any> {
        return ResponseEntity(
                "Could not update comment",
                HttpStatus.BAD_REQUEST
        )
    }

    @ExceptionHandler(value= [CommentDeletingFailedException::class])
    fun handleCommentDeletingFailedException(commentDeletingFailedException: CommentDeletingFailedException)
            : ResponseEntity<Any> {
        return ResponseEntity(
                "Could not delete comment",
                HttpStatus.BAD_REQUEST
        )
    }
}