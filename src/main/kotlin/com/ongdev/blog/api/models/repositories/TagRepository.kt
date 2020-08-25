package com.ongdev.blog.api.models.repositories

import com.ongdev.blog.api.models.entities.Tag
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface TagRepository : JpaRepository<Tag, UUID> {
    fun findAllByLink(link: String, pageable: Pageable): Page<Tag>
}
