package com.gavinflood.saver.service

import com.gavinflood.saver.config.security.constant.Constants
import com.gavinflood.saver.domain.ApplicationUser
import com.gavinflood.saver.repository.ApplicationUserRepository
import com.gavinflood.saver.repository.RoleRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

/**
 * Business logic for an [ApplicationUser].
 *
 * @param repository The repository to interact with the database layer
 */
@Service
class ApplicationUserService(repository: ApplicationUserRepository, val roleRepository: RoleRepository,
                             private val passwordEncoder: PasswordEncoder) : BaseService<ApplicationUser>(repository) {

    /**
     * Create and save a new user.
     *
     * @param resource The new user
     * @return The persisted user
     */
    override fun create(resource: ApplicationUser): ApplicationUser {
        val password = resource.credential.password
        resource.credential.password = passwordEncoder.encode(password)
        resource.roles.add(roleRepository.findByName(Constants.ROLE_USER).get())
        return super.create(resource)
    }

}