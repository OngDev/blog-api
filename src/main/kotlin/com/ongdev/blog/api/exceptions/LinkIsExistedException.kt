package com.ongdev.blog.api.exceptions

class LinkIsExistedException(
        val entityName: String,
        val key: String,
        val value: String
) :RuntimeException()