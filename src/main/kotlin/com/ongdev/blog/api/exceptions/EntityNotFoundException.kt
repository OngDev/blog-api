package com.ongdev.blog.api.exceptions

import java.lang.RuntimeException

class EntityNotFoundException(
        val entityName: String,
        val key: String,
        val value: String
) : RuntimeException()