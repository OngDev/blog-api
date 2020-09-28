package com.ongdev.blog.api.models.repositories

import com.ongdev.blog.api.models.entities.Article
import com.ongdev.blog.api.models.entities.Category
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ArticleRepository : PagingAndSortingRepository<Article, UUID> {
	fun findAllByTitle(title: String, pageable: Pageable) :Optional< Page<Article>>
	@Query("SELECT a from Article a join a.categories ac " +
			"where ac.id=:category_id")
	fun findAllArticlesByCategoryId(category_id:UUID, pageable: Pageable):Optional<Page<Article>>
}