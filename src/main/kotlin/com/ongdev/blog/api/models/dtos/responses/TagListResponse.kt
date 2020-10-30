package com.ongdev.blog.api.models.dtos.responses

class TagListResponse (
        val result: List<TagResponse>,
        val page: Int,
        val size: Int,
        val totalElements: Int,
        val totalPages: Int
)