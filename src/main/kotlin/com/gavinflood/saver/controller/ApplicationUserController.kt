package com.gavinflood.saver.controller

import com.gavinflood.saver.domain.ApplicationUser
import com.gavinflood.saver.domain.response.sendOkResponse
import com.gavinflood.saver.service.ApplicationUserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

/**
 * REST controller exposing operations for the [ApplicationUser] entity.
 *
 * @param service The service associated with [ApplicationUser]
 */
@RestController
@RequestMapping("/api/users")
class ApplicationUserController(override val service: ApplicationUserService)
    : BaseController<ApplicationUser, ApplicationUserService>(service) {

    /**
     * Register a user.
     *
     * @param user The user data
     * @return The persisted user
     */
    @PostMapping
    fun register(@RequestBody @Valid user: ApplicationUser): ResponseEntity<ApplicationUser> {
        return sendOkResponse(service.create(user))
    }

    /**
     * Find a user.
     *
     * @param id The user ID
     * @return The matching user, if any found
     */
    @GetMapping("/{id}")
    fun findOne(@PathVariable id: Long): ResponseEntity<ApplicationUser> {
        return sendOkResponse(service.findOne(id).orElseThrow())
    }

}