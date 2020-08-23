package com.ongdev.blog.api.models.repositories


import com.ongdev.blog.api.models.entities.Category
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CategoryRepository : JpaRepository<Category, UUID> {
    fun findAllByName(name: String, pageable: Pageable): Page<Category>
    fun findAllByName(name: String): Set<Category>
}