package com.ongdev.blog.api.controllers

import com.ongdev.blog.api.models.dtos.requests.TagRequest
import com.ongdev.blog.api.models.dtos.responses.TagResponse
import com.ongdev.blog.api.models.dtos.responses.TagsResponse
import com.ongdev.blog.api.services.interfaces.TagService
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("tags")
class TagController(private val tagService: TagService) {

    @GetMapping("{id}")
    fun getTagById(@PathVariable(name = "id", required = true) id: String): ResponseEntity<TagResponse> {
        val tag = tagService.getTagById(id)
        return ResponseEntity(tag, HttpStatus.OK)
    }

    @GetMapping
    fun getAllTags(@PageableDefault(size = 10, page = 0) pageable: Pageable): ResponseEntity<TagsResponse> {
        val tags = tagService.getAllTag(pageable)
        return ResponseEntity(tags, HttpStatus.OK)
    }

    @GetMapping("link/{link}")
    fun getAllTagsByLink(@PathVariable(name = "link", required = true) link: String
                         , @PageableDefault(size = 10, page = 0) pageable: Pageable): ResponseEntity<TagsResponse> {
        val tags = tagService.getAllTagByLink(link, pageable)
        return ResponseEntity(tags, HttpStatus.OK)
    }

    @PostMapping
    fun createTag(@RequestBody TagRequest: TagRequest): ResponseEntity<TagResponse> {
        val tag = tagService.addTag(TagRequest)
        return ResponseEntity(tag, HttpStatus.OK)
    }

    @PutMapping("{id}")
    fun updateTagById(@PathVariable(name = "id", required = true) id: String, @RequestBody tagRequest: TagRequest): ResponseEntity<TagResponse> {
        val tag = tagService.updateTagById(id, tagRequest)
        return ResponseEntity(tag, HttpStatus.OK)
    }

    @DeleteMapping("{id}")
    fun deleteTagById(@PathVariable(name = "id", required = true) id: String): ResponseEntity<Void> {
        tagService.deleteTagById(id)
        return ResponseEntity(HttpStatus.OK)
    }
}