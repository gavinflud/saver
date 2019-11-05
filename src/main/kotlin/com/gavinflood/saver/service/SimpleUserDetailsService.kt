package com.gavinflood.saver.service

import com.gavinflood.saver.domain.SimpleUserDetails
import com.gavinflood.saver.repository.ApplicationUserRepository
import com.gavinflood.saver.repository.CredentialRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

/**
 * A simple implementation of the UserDetailsService.
 *
 * @param applicationUserRepository ApplicationUser repository
 * @param credentialRepository Credential repository
 */
@Service
class SimpleUserDetailsService(private val applicationUserRepository: ApplicationUserRepository,
                               private val credentialRepository: CredentialRepository) : UserDetailsService {

    /**
     * Load a user by their username.
     *
     * First, lookup a matching Credential for the username. Then find an ApplicationUser with a foreign key to that
     * credential.
     *
     * @param username The username identifying the user
     * @return The matching user as a UserDetails wrapper
     */
    override fun loadUserByUsername(username: String): UserDetails {
        val credential = credentialRepository
                .findByUsername(username)
                .orElseThrow { UsernameNotFoundException("No Credential found matching the username '$username'") }

        val user = applicationUserRepository
                .findByCredential(credential)
                .orElseThrow { UsernameNotFoundException("No ApplicationUser found matching the credential for '$username'") }

        return SimpleUserDetails(user.id, user.credential.username, user.credential.password, user.getAuthorities())
    }

}