package com.ongdev.blog.api.models.repositories

import com.ongdev.blog.api.models.entities.Article
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ArticleRepository : JpaRepository<Article, UUID>