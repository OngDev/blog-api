package com.ongdev.blog.api.models.entities

import com.fasterxml.jackson.annotation.JsonIgnore
import com.ongdev.blog.api.models.entities.base.BaseEntityAudit
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.ManyToMany

@Entity
class Category(
        var name: String = "",
        var link: String = "", // remove letter accent and replace space with dash '-' from name
        @ManyToMany(fetch = FetchType.EAGER, mappedBy = "categories", cascade = [CascadeType.ALL])
        @JsonIgnore
        var articles: Set<Article> = HashSet()
) : BaseEntityAudit()