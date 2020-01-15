package com.ongdev.blog.api.models.repositories

import com.ongdev.blog.api.models.entities.Article
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface ArticleRepository : JpaRepository<Article, UUID>