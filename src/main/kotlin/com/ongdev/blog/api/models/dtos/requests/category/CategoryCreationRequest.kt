package com.ongdev.blog.api.models.dtos.requests.Category

import com.ongdev.blog.api.models.entities.Article

class CategoryCreationRequest(
        val name: String,
        val link: String
//        ,val articles: Set<Article>
)