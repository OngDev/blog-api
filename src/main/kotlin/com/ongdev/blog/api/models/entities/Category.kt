package com.ongdev.blog.api.models.entities

import com.ongdev.blog.api.models.entities.base.BaseEntityAudit
import javax.persistence.*

@Entity
class Category(
    var name: String = "",
    var link: String = "", // remove letter accent and replace space with dash '-' from name
    @ManyToMany(mappedBy = "categories")
    var articles: Set<Article> = HashSet()
):BaseEntityAudit()