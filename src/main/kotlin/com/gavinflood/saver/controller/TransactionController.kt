package com.gavinflood.saver.controller

import com.gavinflood.saver.domain.Transaction
import com.gavinflood.saver.domain.response.sendOkResponse
import com.gavinflood.saver.repository.TransactionRepository
import com.gavinflood.saver.service.AccountService
import com.gavinflood.saver.service.TransactionService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/**
 * REST controller exposing operations for the [Transaction] entity.
 *
 * @param service The service associated with [Transaction]
 * @param accountService The service for interacting with accounts
 */
@RestController
@RequestMapping("/api/transactions")
class TransactionController(service: TransactionService, private val accountService: AccountService)
    : BaseController<Transaction, TransactionRepository, TransactionService>(service) {

    /**
     * Create a new transaction.
     *
     * @param resource The transaction data
     * @return The persisted transaction
     */
    @PostMapping
    override fun create(resource: Transaction): ResponseEntity<Transaction> {
        return super.create(resource)
    }

    /**
     * Find all transactions belonging to a specific account.
     *
     * @param accountId Identifies the account
     * @param pageable Defines the paging structure
     * @return The found accounts, if any
     */
    @GetMapping
    fun findAllForAccount(@RequestParam accountId: Long, pageable: Pageable): ResponseEntity<Page<Transaction>> {
        val account = accountService.findOne(accountId)
        return sendOkResponse(service.findAllForAccount(account, pageable))
    }

}