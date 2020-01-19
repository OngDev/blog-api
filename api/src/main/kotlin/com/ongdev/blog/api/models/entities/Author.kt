package com.ongdev.blog.api.models.entities

import com.ongdev.blog.api.models.entities.base.BaseEntity
import javax.persistence.Entity
import javax.persistence.OneToMany

@Entity
data class Author(
	@OneToMany(mappedBy = "author") var articles: Set<Article> = HashSet()
) : BaseEntity()
