package com.ongdev.blog.api.models.repositories

import com.ongdev.blog.api.models.entities.Article
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ArticleRepository : PagingAndSortingRepository<Article, UUID> {
	fun findAllByTitle(title: String, pageable: Pageable) : Page<Article>
}