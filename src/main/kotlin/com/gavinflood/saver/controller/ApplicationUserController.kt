package com.gavinflood.saver.controller

import com.gavinflood.saver.domain.ApplicationUser
import com.gavinflood.saver.domain.response.sendOkResponse
import com.gavinflood.saver.repository.ApplicationUserRepository
import com.gavinflood.saver.service.ApplicationUserService
import com.gavinflood.saver.service.SecurityService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

/**
 * REST controller exposing operations for the [ApplicationUser] entity.
 *
 * @param service The service associated with [ApplicationUser]
 * @param securityService Service exposing security functionality
 */
@RestController
@RequestMapping("/api/users")
class ApplicationUserController(service: ApplicationUserService,
                                private val securityService: SecurityService)
    : BaseController<ApplicationUser, ApplicationUserRepository, ApplicationUserService>(service) {

    /**
     * Register a user.
     *
     * @param resource The user data
     * @return The persisted user
     */
    @PostMapping
    override fun create(@RequestBody @Valid resource: ApplicationUser): ResponseEntity<ApplicationUser> {
        return super.create(resource)
    }

    /**
     * Find a user.
     *
     * @param id The user ID
     * @return The matching user, if any found
     */
    @GetMapping("/{id}")
    override fun findOne(@PathVariable id: Long): ResponseEntity<ApplicationUser> {
        return super.findOne(id)
    }

    /**
     * Get the current user.
     *
     * @return The current user, if the authentication is valid
     */
    @GetMapping("/current")
    fun getCurrent(): ResponseEntity<ApplicationUser> {
        return sendOkResponse(securityService.getCurrentUser().get())
    }

}