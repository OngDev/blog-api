package com.ongdev.blog.api.models.dtos.responses.article

import java.util.*

class ArticleCreationResponse(
        val id: String,
        val title: String,
        val description: String,
        val content: String,
        val publishDate: Date?,
        val link: String,
        val nameCategories: List<String>
)

// để đỡ class update article ở đây nha anh :((( kh hiểu sao máy em bị lỗi tạo class tên này nó kh nhận
class ArticleUpdatingResponse(
        val id: String,
        val title: String,
        val description: String,
        val content: String,
        val publishDate: Date?,
        val link: String
)
