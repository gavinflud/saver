package com.gavinflood.saver.service

import com.gavinflood.saver.config.constants.SecurityConstants
import com.gavinflood.saver.domain.ApplicationUser
import com.gavinflood.saver.domain.Credential
import com.gavinflood.saver.domain.exception.ServiceValidationException
import com.gavinflood.saver.repository.ApplicationUserRepository
import com.gavinflood.saver.repository.CredentialRepository
import com.gavinflood.saver.repository.RoleRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

/**
 * Business logic for an [ApplicationUser].
 *
 * @param repository The repository to interact with the database layer
 * @param credentialRepository Repository for the [Credential] entity
 * @param roleRepository Repository for the Role entity
 * @param passwordEncoder The password encoder implementation
 */
@Service
class ApplicationUserService(repository: ApplicationUserRepository, val credentialRepository: CredentialRepository,
                             val roleRepository: RoleRepository, private val passwordEncoder: PasswordEncoder)
    : BaseService<ApplicationUser, ApplicationUserRepository>(repository) {

    /**
     * Create and save a new user.
     *
     * @param resource The new user
     * @return The persisted user
     */
    override fun create(resource: ApplicationUser): ApplicationUser {
        if (!isUsernameUnique(resource.credential.username)) {
            throw ServiceValidationException(mutableMapOf(Pair(Credential::username, "A user with that username already exists")))
        }

        val password = resource.credential.password
        resource.credential.password = passwordEncoder.encode(password)
        resource.roles.add(roleRepository.findByName(SecurityConstants.ROLE_USER).get())
        return super.create(resource)
    }

    /**
     * Check if a username is unique.
     *
     * @param username The username to check
     * @return True if the username is unique, false otherwise
     */
    private fun isUsernameUnique(username: String): Boolean {
        return credentialRepository.findByUsername(username).isEmpty
    }

}