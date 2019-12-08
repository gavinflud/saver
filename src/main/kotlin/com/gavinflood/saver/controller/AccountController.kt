package com.gavinflood.saver.controller

import com.gavinflood.saver.domain.Account
import com.gavinflood.saver.domain.dto.AccountDTO
import com.gavinflood.saver.domain.exception.MethodNotImplementedException
import com.gavinflood.saver.domain.response.sendOkResponse
import com.gavinflood.saver.repository.AccountRepository
import com.gavinflood.saver.service.AccountService
import com.gavinflood.saver.service.AccountTypeService
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
class AccountController(service: AccountService, private val accountTypeService: AccountTypeService)
    : BaseController<Account, AccountRepository, AccountService>(service) {

    override fun create(@RequestBody resource: Account): ResponseEntity<Account> {
        throw MethodNotImplementedException()
    }

    /**
     * Create a new account.
     *
     * @param dto DTO representing the account
     * @return The persisted account
     */
    @PostMapping
    fun create(@RequestBody dto: AccountDTO): ResponseEntity<Account> {
        val accountType = accountTypeService.findOne(dto.accountTypeId)
        val account = Account(dto.name, accountType)
        return super.create(account)
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

    override fun update(@PathVariable id: Long, @RequestBody resource: Account): ResponseEntity<Account> {
        throw MethodNotImplementedException()
    }

    /**
     * Update an account.
     *
     * @param id Identifies the account
     * @param dto DTO representing the account
     * @return The updated account
     */
    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody dto: AccountDTO): ResponseEntity<Account> {
        return sendOkResponse(service.update(id, dto))
    }

}