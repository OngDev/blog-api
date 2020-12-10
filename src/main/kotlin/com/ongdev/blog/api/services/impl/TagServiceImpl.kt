package com.ongdev.blog.api.services.impl

import com.ongdev.blog.api.exceptions.EntityCreationFailedException
import com.ongdev.blog.api.exceptions.EntityDeletingFailedException
import com.ongdev.blog.api.exceptions.EntityNotFoundException
import com.ongdev.blog.api.exceptions.EntityUpdatingFailedException
import com.ongdev.blog.api.models.dtos.requests.TagRequest
import com.ongdev.blog.api.models.dtos.responses.TagListResponse
import com.ongdev.blog.api.models.dtos.responses.TagResponse
import com.ongdev.blog.api.models.repositories.TagRepository
import com.ongdev.blog.api.models.toTag
import com.ongdev.blog.api.models.toTagResponse
import com.ongdev.blog.api.models.toTagsResponse
import com.ongdev.blog.api.models.update
import com.ongdev.blog.api.services.interfaces.TagService
import org.springframework.stereotype.Service
import org.springframework.data.domain.Pageable

import java.util.*

@Service
class TagServiceImpl(val tagRepository: TagRepository) : TagService {
    override fun addTag(tagRequest: TagRequest) : TagResponse {
        try {
            return tagRepository.save(tagRequest.toTag()).toTagResponse()
        } catch (ex: IllegalArgumentException) {
            throw EntityCreationFailedException("tag")
        }
    }

    override fun getTagById(id: String): TagResponse {
        val tag = tagRepository.findById(UUID.fromString(id)).orElseThrow{
            EntityNotFoundException("tag", "id", id)
        }
        return tag.toTagResponse()
    }

    override fun getAllTagByLink(link: String, pageable: Pageable): TagListResponse {
        val tags = tagRepository.findAllByLink(link, pageable)
        val tagList = tags.toTagsResponse()
        return TagListResponse(tagList.toList(), tags.number, tags.size, tags.numberOfElements, tags.totalPages)
    }

    override fun getAllTag(pageable: Pageable): TagListResponse {
        val tags = tagRepository.findAll(pageable)
        val tagList = tags.toTagsResponse()
        return TagListResponse(tagList.toList(), tags.number, tags.size, tags.numberOfElements, tags.totalPages)
    }

    override fun updateTagById(id: String, tagRequest: TagRequest): TagResponse {
        val tag = tagRepository.findById(UUID.fromString(id)).orElseThrow{
            EntityNotFoundException("tag", "id", id)
        }
        tag.update(tagRequest)
        try {
            return tagRepository.save(tag).toTagResponse()
        } catch (ex: IllegalArgumentException) {
            throw EntityUpdatingFailedException("tag")
        }
    }

    override fun deleteTagById(id: String) {
        if (tagRepository.existsById(UUID.fromString(id)).not()) {
            throw EntityNotFoundException("tag", "id", id)
        }
        try {
            return tagRepository.deleteById(UUID.fromString(id))
        } catch (ex: IllegalArgumentException) {
            throw EntityDeletingFailedException("tag")
        }
    }
}