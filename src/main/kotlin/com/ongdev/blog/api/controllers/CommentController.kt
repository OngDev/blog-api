package com.ongdev.blog.api.controllers

import com.ongdev.blog.api.models.dtos.requests.CommentCreationRequest
import com.ongdev.blog.api.models.dtos.requests.CommentUpdatingRequest
import com.ongdev.blog.api.models.dtos.responses.CommentResponse
import com.ongdev.blog.api.models.dtos.responses.CommentsWithPaginationResponse
import com.ongdev.blog.api.services.interfaces.CommentService
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("comments")
class CommentController(private val commentService: CommentService) {

    @GetMapping("{commentId}")
    fun getCommentById(@PathVariable(name = "commentId", required = true) commentId: String): ResponseEntity<CommentResponse> {
        return ResponseEntity(commentService.getCommentById(commentId), HttpStatus.OK)
    }

    @PostMapping
    fun createComment(
            @RequestBody commentCreationRequest: CommentCreationRequest): ResponseEntity<CommentResponse> {
        val commentCreationResponse = commentService.createComment(commentCreationRequest)
        return ResponseEntity(commentCreationResponse, HttpStatus.OK)
    }

    @PutMapping("{commentId}")
    fun updateCommentById(
            @PathVariable(name = "commentId", required = true) commentId: String,
            @RequestBody commentUpdatingRequest: CommentUpdatingRequest
    ): ResponseEntity<CommentResponse> = ResponseEntity(
            commentService.updateCommentById(commentId, commentUpdatingRequest),
            HttpStatus.OK)

    @DeleteMapping("{commentId}")
    fun deleteCommentById(@PathVariable(name = "commentId", required = true) commentId: String): ResponseEntity<Void> {
        commentService.deleteCommentById(commentId)
        return ResponseEntity(HttpStatus.OK)
    }

    @GetMapping("article/{articleId}")
    fun getCommentByArticle(@PathVariable(name = "articleId", required = true) articleId: String
                            , @PageableDefault(size = 10
                    , page = 0
                    , sort = ["createAt"]) pageable: Pageable): ResponseEntity<CommentsWithPaginationResponse> {
        return ResponseEntity(commentService.getCommentsByArticle(articleId, pageable), HttpStatus.OK)
    }
}
