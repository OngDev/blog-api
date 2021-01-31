package com.ongdev.blog.api.models

import com.ongdev.blog.api.models.dtos.requests.CommentCreationRequest
import com.ongdev.blog.api.models.dtos.requests.CommentUpdatingRequest
import com.ongdev.blog.api.models.dtos.responses.ChildResponse
import com.ongdev.blog.api.models.dtos.responses.CommentResponse
import com.ongdev.blog.api.models.entities.Comment
import org.springframework.data.domain.Page

fun Comment.toChildResponse() = ChildResponse(
        id.toString(),
        userId.toString(),
        content = content,
        createAt = createAt
)

fun CommentCreationRequest.toCommentEntity() = Comment(
        userId = userId,
        content = content
)

fun Comment.toCommentCreationResponse(children: List<ChildResponse>?) = CommentResponse(
        commentId = id.toString(),
        userId = userId.toString(),
        content = content,
        createAt = createAt,
        children = children?.toSet()
)

fun Comment.toCommentUpdatingResponse(children: List<ChildResponse>?) = CommentResponse(
        id.toString(),
        userId.toString(),
        content = content,
        createAt = createAt,
        children = children?.toSet()
)

fun CommentUpdatingRequest.toCommentEntity(comment: Comment): Comment {
    comment.content = content
    return comment
}

fun Set<Comment>.toChildrenResponse() = map {
    it.toChildResponse()
}

fun Page<Comment>.toPageCommentResponse() = map {
    it.toCommentCreationResponse(it.children?.toChildrenResponse())
}