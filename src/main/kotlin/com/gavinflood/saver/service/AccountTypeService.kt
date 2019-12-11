package com.gavinflood.saver.service

import com.gavinflood.saver.domain.AccountType
import com.gavinflood.saver.repository.AccountTypeRepository
import org.springframework.stereotype.Service
import javax.persistence.NoResultException

/**
 * Business logic for an [AccountType].
 *
 * @param repository The repository to interact with the database layer
 */
@Service
class AccountTypeService(repository: AccountTypeRepository)
    : BaseService<AccountType, AccountTypeRepository>(repository) {

    /**
     * Find one AccountType based on the code identifier.
     *
     * @param code Identifies the account type
     * @return The account type, if found
     */
    fun findOne(code: String): AccountType {
        val result = repository.findByCode(code)
        if (result.isPresent) {
            return result.get()
        }

        throw NoResultException("No AccountType with code '$code' found")
    }

}