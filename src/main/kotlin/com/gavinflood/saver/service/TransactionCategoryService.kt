package com.gavinflood.saver.service

import com.gavinflood.saver.domain.TransactionCategory
import com.gavinflood.saver.repository.TransactionCategoryRepository
import org.springframework.stereotype.Service
import javax.persistence.NoResultException

/**
 * Business logic for the [TransactionCategory].
 *
 * @param repository The repository to interact with the database layer
 */
@Service
class TransactionCategoryService(repository: TransactionCategoryRepository,
                                 private val securityService: SecurityService)
    : BaseService<TransactionCategory, TransactionCategoryRepository>(repository) {

    /**
     * Find one TransactionCategory based on the code identifier.
     *
     * @param code Identifies the transaction category
     * @return The transaction category, if found
     */
    fun findOne(code: String): TransactionCategory {
        val result = repository.findByCode(code)
        if (result.isPresent) {
            return result.get()
        }

        throw NoResultException("No AccountType with code '$code' found")
    }

    override fun findAll(): List<TransactionCategory> {
        val currentUser = securityService.getCurrentUser().orElseThrow()
        return repository.findAllByUserIsNullOrUserEquals(currentUser)
    }

}