package com.ongdev.blog.api.models.entities

import com.ongdev.blog.api.models.entities.base.BaseEntityAudit
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity
data class Article(
	var title: String = "",
	var description: String = "",
	@ManyToOne @JoinColumn(name = "author_id", nullable = false) var author: Author? = null
) : BaseEntityAudit()
