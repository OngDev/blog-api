package com.ongdev.blog.api.models.dtos.responses

import java.util.*

class ChildResponse (
        var id: String,
        var userId: String,
        var content: String,
        var createAt: Date?
)