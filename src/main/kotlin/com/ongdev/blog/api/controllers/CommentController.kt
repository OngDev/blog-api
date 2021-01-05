package com.ongdev.blog.api.controllers

import com.ongdev.blog.api.models.dtos.requests.CommentCreationRequest
import com.ongdev.blog.api.models.dtos.requests.CommentUpdatingRequest
import com.ongdev.blog.api.models.dtos.responses.CommentResponse
import com.ongdev.blog.api.services.interfaces.CommentService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("comments")
class CommentController(private val commentService: CommentService) {

    @GetMapping("{id}")
    fun getComment(@PathVariable(name = "id", required = true) id: String): ResponseEntity<CommentResponse> {
        return ResponseEntity(commentService.getComment(id), HttpStatus.OK)
    }

    @PostMapping
    fun createComment(
            @RequestBody commentCreationRequest: CommentCreationRequest): ResponseEntity<CommentResponse> {
        val commentCreationResponse = commentService.createComment(commentCreationRequest)
        return ResponseEntity(commentCreationResponse, HttpStatus.OK)
    }

    @PutMapping("{id}")
    fun updateComment(
            @PathVariable(name = "id", required = true) id: String,
            @RequestBody commentUpdatingRequest: CommentUpdatingRequest
    ): ResponseEntity<CommentResponse> = ResponseEntity(
            commentService.updateComment(commentUpdatingRequest, id),
            HttpStatus.OK)

    @DeleteMapping("{id}")
    fun deleteComment(@PathVariable(name = "id", required = true) id: String): ResponseEntity<Void> {
        commentService.deleteComment(id)
        return ResponseEntity(HttpStatus.OK)
    }
}
