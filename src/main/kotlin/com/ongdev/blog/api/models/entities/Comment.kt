package com.ongdev.blog.api.models.entities

import com.ongdev.blog.api.models.entities.base.BaseEntityAudit
import java.util.*
import javax.persistence.*
import kotlin.collections.HashSet

@Entity
class Comment(
		var userId: UUID? = null,
		var content: String = "",

		@ManyToOne(fetch = FetchType.LAZY)
		@JoinColumn(name = "article_id")
		var article: Article? = null,

		@ManyToOne(fetch = FetchType.LAZY)
		@JoinColumn(name = "parent_id")
		var parent: Comment? = null,

		@OneToMany(mappedBy = "parent", cascade = [CascadeType.ALL])
		var children: Set<Comment> = HashSet()
) : BaseEntityAudit()
