package com.ongdev.blog.api.models.repositories

import com.ongdev.blog.api.models.entities.Article
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ArticleRepository : PagingAndSortingRepository<Article, UUID> {
    fun findAllByTitle(title: String, pageable: Pageable): Page<Article>

    fun existsByTitle(title: String):Boolean

	@Query("SELECT a from Article a join a.categories ac on ac.id=:category_id")
	fun findAllArticlesByCategoryId(category_id:UUID, pageable: Pageable):Page<Article>

	@Query("SELECT a from Article a join a.tags at on at.id=:tag_id")
	fun findAllArticlesByTagId(tag_id:UUID, pageable: Pageable):Page<Article>

	@Query("SELECT a from Article a join a.tags at on at.link=:tag_link")
	fun findAllArticlesByTagLink(tag_link:String, pageable: Pageable):Page<Article>
}