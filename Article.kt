package com.ongdev.blog.api.models.entities

import com.ongdev.blog.api.models.entities.base.BaseEntityAudit
import java.util.*
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import kotlin.collections.HashSet

@Entity
data class Article(
	var title: String = "",
	var description: String = "",
	var content: String = "",
	var name: String = "",
	var publishDate: Date? = null,
	var link: String = ""
	//@OneToMany(mappedBy = "article") var comments: Set<Comment> = HashSet(),
	//@ManyToOne @JoinColumn(name = "author_id", nullable = false) var author: Author? = null
) : BaseEntityAudit()
