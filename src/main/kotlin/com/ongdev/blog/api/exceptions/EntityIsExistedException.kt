package com.ongdev.blog.api.exceptions

class EntityIsExistedException(
        val entityName: String,
        val key: String,
        val value: String
) :RuntimeException()