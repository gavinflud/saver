package com.gavinflood.saver.controller

import com.gavinflood.saver.domain.TransactionCategory
import com.gavinflood.saver.domain.response.sendOkResponse
import com.gavinflood.saver.repository.TransactionCategoryRepository
import com.gavinflood.saver.service.TransactionCategoryService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * REST controller exposing operations for the [TransactionCategory] entity.
 *
 * @param service The service associated with [TransactionCategory]
 */
@RestController
@RequestMapping("/api/transactioncategories")
class TransactionCategoryController(service: TransactionCategoryService)
    : BaseController<TransactionCategory, TransactionCategoryRepository, TransactionCategoryService>(service) {

    /**
     * Get all transaction categories available to the current user.
     *
     * @return The found transaction categories
     */
    @GetMapping
    fun findAll(): ResponseEntity<List<TransactionCategory>> {
        return sendOkResponse(service.findAll())
    }

}