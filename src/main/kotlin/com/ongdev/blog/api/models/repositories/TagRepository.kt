package com.ongdev.blog.api.models.repositories

import com.ongdev.blog.api.models.entities.Author
import com.ongdev.blog.api.models.entities.Tag
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface TagRepository : JpaRepository<Tag, UUID>
