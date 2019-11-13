package com.gavinflood.saver.repository

import com.gavinflood.saver.domain.Account
import com.gavinflood.saver.domain.ApplicationUser
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

/**
 * Interface for operations on the [Account] repository.
 */
@Repository
interface AccountRepository : BaseRepository<Account> {

    /**
     * Find the accounts for a specific user.
     *
     * @param user The user
     * @param pageable Defines the result paging structure
     * @return The found accounts
     */
    fun findByUsersContains(user: ApplicationUser, pageable: Pageable): Page<Account>

}