package com.ongdev.blog.api.models.entities

import com.ongdev.blog.api.models.entities.base.BaseEntityAudit
import javax.persistence.*

@Entity
data class Category(
    var name: String = "",
    @ManyToMany
    @JoinTable(name = "article_category",
            joinColumns = [JoinColumn(name = "category_id", referencedColumnName = "id")],
            inverseJoinColumns = [JoinColumn(name = "article_id", referencedColumnName = "id")])
    var articles: Set<Article> = HashSet()
):BaseEntityAudit()