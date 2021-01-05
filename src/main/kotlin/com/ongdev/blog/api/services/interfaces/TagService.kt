package com.ongdev.blog.api.services.interfaces

import com.ongdev.blog.api.models.dtos.requests.TagRequest
import com.ongdev.blog.api.models.dtos.responses.TagsResponse
import com.ongdev.blog.api.models.dtos.responses.TagResponse
import org.springframework.data.domain.Pageable

interface TagService {
    fun addTag(tagRequest: TagRequest): TagResponse
    fun getTagById(id: String): TagResponse
    fun getAllTagByLink(link: String, pageable: Pageable): TagsResponse
    fun getAllTag(pageable: Pageable): TagsResponse
    fun updateTagById(id: String, tagRequest: TagRequest): TagResponse
    fun deleteTagById(id: String)
}