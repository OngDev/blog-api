package com.ongdev.blog.api.models.dtos.responses.category

import com.ongdev.blog.api.models.entities.Article

class CategoryCreationResponse (
    val id: String,
    val name: String,
    val link: String
//    ,val articles: Set<Article>
)