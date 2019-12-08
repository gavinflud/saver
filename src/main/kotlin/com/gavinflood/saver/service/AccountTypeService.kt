package com.gavinflood.saver.service

import com.gavinflood.saver.domain.AccountType
import com.gavinflood.saver.repository.AccountTypeRepository
import org.springframework.stereotype.Service
import javax.persistence.NoResultException

@Service
class AccountTypeService(repository: AccountTypeRepository)
    : BaseService<AccountType, AccountTypeRepository>(repository) {

    fun findOne(code: String): AccountType {
        val result = repository.findByCode(code)
        if (result.isPresent) {
            return result.get()
        }

        throw NoResultException("No AccountType with code '$code' found")
    }

}