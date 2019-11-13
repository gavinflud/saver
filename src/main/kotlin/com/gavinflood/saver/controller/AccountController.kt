package com.gavinflood.saver.controller

import com.gavinflood.saver.domain.Account
import com.gavinflood.saver.repository.AccountRepository
import com.gavinflood.saver.service.AccountService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/**
 * REST controller exposing operations for the [Account] entity.
 *
 * @param service The service associated with [Account]
 */
@RestController
@RequestMapping("/api/accounts")
class AccountController(service: AccountService) : BaseController<Account, AccountRepository, AccountService>(service) {

    /**
     * Create a new account.
     *
     * @param resource The account to create
     * @return The persisted account
     */
    @PostMapping
    override fun create(@RequestBody resource: Account): ResponseEntity<Account> {
        return super.create(resource)
    }

    /**
     * Get all accounts belonging to the current user.
     *
     * @param pageable Defines the paging structure for the results
     * @return The found accounts in [Page] format
     */
    @GetMapping
    override fun findAll(pageable: Pageable): ResponseEntity<Page<Account>> {
        return super.findAll(pageable)
    }

    /**
     * Get a single account.
     *
     * @param id Identifies the account
     * @return The found account
     */
    @GetMapping("/{id}")
    override fun findOne(@PathVariable id: Long): ResponseEntity<Account> {
        return super.findOne(id)
    }

}