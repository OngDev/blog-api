package com.ongdev.blog.api.models

import com.ongdev.blog.api.models.dtos.requests.CommentCreationRequest
import com.ongdev.blog.api.models.dtos.requests.CommentUpdatingRequest
import com.ongdev.blog.api.models.dtos.responses.ChildCreationResponse
import com.ongdev.blog.api.models.dtos.responses.CommentCreationResponse
import com.ongdev.blog.api.models.dtos.responses.CommentUpdatingResponse
import com.ongdev.blog.api.models.entities.Comment

fun Comment.toChildCreationResponse() = ChildCreationResponse(
        id.toString(),
        userId.toString(),
        content = content,
        createAt = createAt
)

fun CommentCreationRequest.toCommentEntity() = Comment(
        content = content,
        createAt = createAt,
        article = article,
        parent = parent,
        children = children
)

fun Comment.toCommentCreationResponse(children: List<ChildCreationResponse>?) = CommentCreationResponse(
        id = id.toString(),
        userId = userId.toString(),
        content = content,
        createAt = createAt,
        children = children?.toSet()
)

fun Comment.toCommentUpdatingResponse(children: List<ChildCreationResponse>?) = CommentUpdatingResponse(
        id.toString(),
        userId.toString(),
        content = content,
        createAt = createAt,
        children = children?.toSet()
)

fun CommentUpdatingRequest.toComment(comment: Comment): Comment {
    comment.content = content
    comment.createAt = createAt
    return comment
}