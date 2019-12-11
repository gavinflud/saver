package com.gavinflood.saver.repository

import com.gavinflood.saver.domain.ApplicationUser
import com.gavinflood.saver.domain.TransactionCategory
import org.springframework.stereotype.Repository
import java.util.*

/**
 * Interface for operations on the [TransactionCategory] repository.
 */
@Repository
interface TransactionCategoryRepository : BaseRepository<TransactionCategory> {

    /**
     * Find a TransactionCategory using its unique code.
     *
     * @param code The code
     * @return The found AccountType, if any
     */
    fun findByCode(code: String): Optional<TransactionCategory>

    fun findAllByUserIsNullOrUserEquals(applicationUser: ApplicationUser): List<TransactionCategory>

}