package com.ongdev.blog.api.models.entities

import com.fasterxml.jackson.annotation.JsonIgnore
import com.ongdev.blog.api.models.entities.base.BaseEntityAudit
import java.util.*
import javax.persistence.*
import kotlin.collections.HashSet

@Entity
class Article(
		@Column(unique=true)
		var title: String = "",
		var description: String = "",
		var content: String = "",
		var link: String = "", // remove letter accent and replace space with dash '-' from title
		var publishDate: Date? = null,

//		@ManyToOne(fetch = FetchType.LAZY)
//		@JoinColumn(name = "author_id", nullable = false)
//		var author: Author? = null,

		@JsonIgnore
		@OneToMany(mappedBy = "article", cascade = [CascadeType.REMOVE], orphanRemoval = true)
		var comments: Set<Comment> = HashSet(),

		@ManyToMany
		@JoinTable(name = "article_category",
		joinColumns = [JoinColumn(name = "article_id", referencedColumnName = "id")],
		inverseJoinColumns = [JoinColumn(name = "category_id", referencedColumnName = "id")])
		var categories: Set<Category> = HashSet(),

		@ManyToMany
		@JoinTable(name = "article_tag",
		joinColumns = [JoinColumn(name = "article_id", referencedColumnName = "id")],
		inverseJoinColumns = [JoinColumn(name = "tag_id", referencedColumnName = "id")])
		var tags: Set<Tag> = HashSet()
) : BaseEntityAudit()
