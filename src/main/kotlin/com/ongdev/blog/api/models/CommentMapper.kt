package com.ongdev.blog.api.models

import com.ongdev.blog.api.models.dtos.requests.CommentCreationRequest
import com.ongdev.blog.api.models.dtos.requests.CommentUpdatingRequest
import com.ongdev.blog.api.models.dtos.responses.ChildResponse
import com.ongdev.blog.api.models.dtos.responses.CommentResponse
import com.ongdev.blog.api.models.entities.Comment

fun Comment.toChildResponse() = ChildResponse(
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

fun Comment.toCommentResponse(children: List<ChildResponse>?) = CommentResponse(
        id = id.toString(),
        userId = userId.toString(),
        content = content,
        createAt = createAt,
        children = children?.toSet()
)

fun CommentUpdatingRequest.toCommentEntity(comment: Comment): Comment {
    comment.content = content
    comment.createAt = createAt
    return comment
}

fun Set<Comment>.toChildrenResponse() = map {
    it.toChildResponse()
}