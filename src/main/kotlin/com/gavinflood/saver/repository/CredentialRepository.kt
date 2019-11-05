package com.gavinflood.saver.repository

import com.gavinflood.saver.domain.Credential
import org.springframework.stereotype.Repository
import java.util.*

/**
 * Interface for operations on the Credential repository.
 */
@Repository
interface CredentialRepository : BaseRepository<Credential> {

    /**
     * Find a Credential based on the username.
     *
     * @param username The unique username
     * @return The credential matching the username
     */
    fun findByUsername(username: String): Optional<Credential>

}