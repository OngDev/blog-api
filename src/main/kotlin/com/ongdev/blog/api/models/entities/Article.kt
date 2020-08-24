package com.ongdev.blog.api.models.entities

import com.fasterxml.jackson.annotation.JsonIgnore
import com.ongdev.blog.api.models.entities.base.BaseEntityAudit
import java.util.*
import javax.persistence.*
import kotlin.collections.HashSet

@Entity
class Article(
        var title: String = "",
        var description: String = "",
        var content: String = "",
        var name: String = "",
        var publishDate: Date? = null,
        var link: String = "",
        @ManyToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
        @JsonIgnore
        @JoinTable(name = "article_category",
                joinColumns = [JoinColumn(name = "article_id")],
                inverseJoinColumns = [JoinColumn(name = "category_id")])
        var categories: Set<Category> = HashSet()
) : BaseEntityAudit()
