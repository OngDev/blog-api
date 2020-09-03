package com.ongdev.blog.api.models.entities.base

import org.hibernate.annotations.GenericGenerator
import java.io.Serializable
import java.util.*
import javax.persistence.Column
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.MappedSuperclass

@MappedSuperclass
abstract class BaseEntity : Serializable {
    companion object {
        private const val serialVersionUID = -5554308939380869754L
    }

    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @GeneratedValue(generator = "uuid")
    @Column(unique = true, nullable = false, updatable = false, columnDefinition = "BINARY(16)")
    var id: UUID? = null
}