package com.ongdev.blog.api.services.interfaces

import com.ongdev.blog.api.models.entities.Article

interface ArticleService {
    fun createPost(article: Article) : Article
}
