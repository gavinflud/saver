package com.gavinflood.saver.controller

import com.gavinflood.saver.domain.Type
import com.gavinflood.saver.domain.response.sendOkResponse
import com.gavinflood.saver.repository.TypeRepository
import com.gavinflood.saver.service.TypeService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * REST controller exposing operations for the [Type] entity.
 *
 * @param service The service associated with [Type]
 */
@RestController
@RequestMapping("/api/types")
class TypeController(service: TypeService) : BaseController<Type, TypeRepository, TypeService>(service) {

    /**
     * Get all types for a particular group.
     *
     * @param group The group the types belong to
     * @return The found types
     */
    @GetMapping("/{group}")
    fun findAll(@PathVariable group: String): ResponseEntity<List<Type>> {
        return sendOkResponse(service.findAll(group))
    }

}