package com.ongdev.blog.api.models.repositories

import com.ongdev.blog.api.models.entities.Article
import com.ongdev.blog.api.models.entities.Comment
import org.springframework.data.domain.Page
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.data.domain.Pageable
import java.util.*

@Repository
interface CommentRepository : JpaRepository<Comment, UUID> {
    fun findAllByArticle(article: Article, pageable: Pageable): Page<Comment>
}