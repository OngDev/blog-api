package com.ongdev.blog.api.exceptions

import java.lang.RuntimeException

class EntityUpdatingFailedException (val entityName: String) : RuntimeException()