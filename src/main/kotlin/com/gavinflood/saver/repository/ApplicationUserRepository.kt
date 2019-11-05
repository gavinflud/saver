package com.gavinflood.saver.repository

import com.gavinflood.saver.domain.Account
import com.gavinflood.saver.domain.ApplicationUser
import com.gavinflood.saver.domain.Credential
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

/**
 * Interface for operations on the ApplicationUser repository.
 */
interface ApplicationUserRepository : BaseRepository<ApplicationUser> {

    /**
     * Find a user based on their credential.
     *
     * @param credential The credential the ApplicationUser should have a foreign key to
     * @return The matching ApplicationUser, if a match exists
     */
    fun findByCredential(credential: Credential): Optional<ApplicationUser>

    /**
     * Find the set of Accounts that an ApplicationUser has access to.
     *
     * @param credential The credential the ApplicationUser should have a foreign key to
     * @return The set of Accounts that have a foreign key to the ApplicationUser
     */
    @Query("SELECT u.accounts FROM gf_application_user u WHERE u.credential = :credential")
    fun findAccountsByCredential(@Param("credential") credential: Credential): Set<Account>

}