package com.ongdev.blog.api.models.entities

import com.ongdev.blog.api.models.entities.base.BaseEntity
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.OneToMany

@Entity
class Author(
        @OneToMany(mappedBy = "author", cascade = [CascadeType.REMOVE])
        var articles: Set<Article> = HashSet()
) : BaseEntity()
