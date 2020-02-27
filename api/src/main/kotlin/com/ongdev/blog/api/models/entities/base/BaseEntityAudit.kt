package com.ongdev.blog.api.models.entities.base

import java.util.*
import javax.persistence.*
import javax.validation.constraints.Size

@MappedSuperclass
abstract class BaseEntityAudit : BaseEntity() {
	@Column(name = "created_at")
	@Temporal(TemporalType.TIMESTAMP)
	private var createdAt: Date? = null

	@Size(max = 20)
	@Column(name = "created_by", length = 20)
	private var createdBy: String = ""

	@Column(name = "updated_at")
	@Temporal(TemporalType.TIMESTAMP)
	private var updatedAt: Date? = null

	@Size(max = 20)
	@Column(name = "updated_by", length = 20)
	private var updatedBy: String = ""

	@Column(name = "is_activated", nullable = false)
	private var isActivated: Boolean = true
	/**
	 * Sets createdAt before insert
	 */
	@PrePersist
	fun setCreationDate() {
		this.createdAt = Date()
	}

	/**
	 * Sets updated before update
	 */
	@PreUpdate
	fun setChangeDate() {
		this.updatedAt = Date()
	}
}