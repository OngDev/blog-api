package com.ongdev.blog.api.models.dtos.responses

import java.util.*

class ChildCreationResponse (
        var id: String,
        var userId: String,
        var content: String,
        var createAt: Date?
)