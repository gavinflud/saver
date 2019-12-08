package com.gavinflood.saver.repository

import com.gavinflood.saver.domain.AccountType
import org.springframework.stereotype.Repository
import java.util.*

/**
 * Interface for operations on the [AccountType] repository.
 */
@Repository
interface AccountTypeRepository : BaseRepository<AccountType> {

    /**
     * Find an AccountType using its unique code.
     *
     * @param code The code
     * @return The found AccountType, if any
     */
    fun findByCode(code: String): Optional<AccountType>

}