package com.ongdev.blog.api.exceptions

import java.lang.RuntimeException

class EntityCreationFailedException (val entityName: String) : RuntimeException()