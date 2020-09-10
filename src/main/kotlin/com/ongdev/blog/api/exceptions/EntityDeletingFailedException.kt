package com.ongdev.blog.api.exceptions

import java.lang.RuntimeException

class EntityDeletingFailedException (val entityName: String) : RuntimeException()