package com.ongdev.blog.api.models.entities

import com.ongdev.blog.api.models.entities.base.BaseEntityAudit
import java.util.*
import javax.persistence.*
import kotlin.collections.HashSet

@Entity
data class Comment(
        var userId: UUID? = null,
        var content: String = "",
        @ManyToOne @JoinColumn(name = "article_id") var article: Article? = null,
        @ManyToOne(cascade = [CascadeType.ALL]) @JoinColumn(name = "parent_id") var parent: Comment? = null,
        @OneToMany(mappedBy = "parent") var children: Set<Comment> = HashSet()
) : BaseEntityAudit()
