package com.ongdev.blog.api.models.entities

import com.ongdev.blog.api.models.entities.base.BaseEntityAudit
import javax.persistence.*

@Entity
class Tag(
    var name: String = "",
    var link: String = "", // remove letter accent and replace space with dash '-' from name
    @ManyToMany
    @JoinTable(name = "article_tag",
        joinColumns = [JoinColumn(name = "tag_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "article_id", referencedColumnName = "id")])
    var articles: Set<Article> = HashSet()
): BaseEntityAudit()