package com.ongdev.blog.api.models.entities

import com.ongdev.blog.api.models.entities.base.BaseEntityAudit
import java.util.*
import javax.persistence.Entity
import javax.persistence.ManyToMany
import kotlin.collections.HashSet

@Entity
data class Article(
        var title: String = "",
        var description: String = "",
        var content: String = "",
        var name: String = "",
        var publishDate: Date? = null,
        var link: String = "",
        @ManyToMany(mappedBy = "articles")
        var categories: Set<Category> = HashSet()
) : BaseEntityAudit()
