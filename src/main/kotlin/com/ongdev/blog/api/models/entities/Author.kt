package com.ongdev.blog.api.models.entities

import com.ongdev.blog.api.models.entities.base.BaseEntity
import java.util.*
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.OneToMany
import kotlin.collections.HashSet

@Entity
class Author(
		@OneToMany(mappedBy = "author", cascade = [CascadeType.REMOVE])
		var articles: Set<Article> = HashSet()
) : BaseEntity()
