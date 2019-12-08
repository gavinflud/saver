package com.gavinflood.saver.controller

import com.gavinflood.saver.domain.AccountType
import com.gavinflood.saver.domain.response.sendOkResponse
import com.gavinflood.saver.repository.AccountTypeRepository
import com.gavinflood.saver.service.AccountTypeService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/accounttypes")
class AccountTypeController(service: AccountTypeService)
    : BaseController<AccountType, AccountTypeRepository, AccountTypeService>(service) {

    /**
     * Get all account types
     *
     * @return The found account types
     */
    @GetMapping
    fun findAll(): ResponseEntity<List<AccountType>> {
        return sendOkResponse(service.findAll())
    }

}