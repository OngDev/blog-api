package com.ongdev.blog.api.models.dtos.responses

import org.springframework.data.domain.Page

class CommentsWithPaginationResponse (val result: Page<CommentResponse>)