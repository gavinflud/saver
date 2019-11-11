package com.gavinflood.saver.service

import com.gavinflood.saver.domain.ApplicationUser
import com.gavinflood.saver.repository.ApplicationUserRepository
import com.gavinflood.saver.repository.CredentialRepository
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.util.*

/**
 * A service that provides security functionality.
 */
@Service
class SecurityService(private val credentialRepository: CredentialRepository,
                      private val applicationUserRepository: ApplicationUserRepository) {

    /**
     * Get the current user if one is authenticated and valid.
     *
     * @return An [Optional] that will contain the current user if one exists
     */
    fun getCurrentUser(): Optional<ApplicationUser> {
        val userDetails = SecurityContextHolder.getContext().authentication.principal
        if (userDetails != null) {
            val credential = credentialRepository.findByUsername((userDetails as UserDetails).username)

            if (credential.isPresent) {
                return applicationUserRepository.findByCredential(credential.get())
            }

            return Optional.empty()
        }

        return Optional.empty()
    }

}