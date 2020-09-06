package com.ongdev.blog.api.models.dtos.requests

import com.ongdev.blog.api.models.entities.Article
import com.ongdev.blog.api.models.entities.Comment
import java.util.*


class CommentCreationRequest(
        val content: String,
        val createAt: Date?,
        val article: Article?,
        val parent: Comment?,
        val children: Set<Comment>?
)
