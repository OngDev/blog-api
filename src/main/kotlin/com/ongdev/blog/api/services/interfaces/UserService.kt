package com.ongdev.blog.api.services.interfaces

import com.ongdev.blog.api.models.auth.UserInfo

interface UserService {
    fun getUserInfoFromToken(token: String?) : UserInfo?
}