package com.gavinflood.saver.config.preload

import com.gavinflood.saver.config.Properties
import com.gavinflood.saver.config.constants.SecurityConstants
import com.gavinflood.saver.domain.ApplicationUser
import com.gavinflood.saver.domain.Credential
import com.gavinflood.saver.domain.Permission
import com.gavinflood.saver.domain.Role
import com.gavinflood.saver.repository.ApplicationUserRepository
import com.gavinflood.saver.repository.CredentialRepository
import com.gavinflood.saver.repository.PermissionRepository
import com.gavinflood.saver.repository.RoleRepository
import org.springframework.context.ApplicationListener
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component


/**
 * Populates the database with any required data prior to startup.
 *
 * @param userRepository User repository
 * @param roleRepository Role repository
 * @param permissionRepository Permission repository
 * @param credentialRepository Credential repository
 * @param passwordEncoder Password encoder
 * @param properties The custom properties wrapper for the application
 */
@Component
class Preload(val userRepository: ApplicationUserRepository, val roleRepository: RoleRepository,
              val permissionRepository: PermissionRepository, val credentialRepository: CredentialRepository,
              val passwordEncoder: PasswordEncoder, val typePreload: TypePreload, val properties: Properties)
    : ApplicationListener<ContextRefreshedEvent> {

    private var hasPreloadAlreadyCompleted = false

    /**
     * Run on application startup and responsible for pre-loading any admin data.
     *
     * @param event Event raised when the [org.springframework.context.ApplicationContext] gets initialized
     */
    override fun onApplicationEvent(event: ContextRefreshedEvent) {
        if (!hasPreloadAlreadyCompleted) {
            typePreload.run()

            val defaultPermission = createOrUpdatePermission(SecurityConstants.PERMISSION_DEFAULT)
            val adminPermission = createOrUpdatePermission(SecurityConstants.PERMISSION_ADMIN)
            val adminPermissions = mutableSetOf(defaultPermission, adminPermission)
            val userPermissions = mutableSetOf(defaultPermission)

            val adminRole = createOrUpdateRole(SecurityConstants.ROLE_ADMIN, adminPermissions)
            createOrUpdateRole(SecurityConstants.ROLE_USER, userPermissions)
            val adminRoles = mutableSetOf(adminRole)

            createOrUpdateUser("Super", "User", "su", properties.adminPassword, adminRoles)
            hasPreloadAlreadyCompleted = true
        }
    }

    /**
     * Create a permission or update it if it exists.
     *
     * @param name The name of the permission
     * @return The permission created/updated
     */
    private fun createOrUpdatePermission(name: String): Permission {
        val permissionResult = permissionRepository.findByName(name)
        val permission = if (permissionResult.isPresent) permissionResult.get() else Permission(name)
        return permissionRepository.save(permission)
    }

    /**
     * Create a role or update it if it exists.
     *
     * @param name The name of the role
     * @param permissions The permissions that relate to the role
     * @return The role created/updated
     */
    private fun createOrUpdateRole(name: String, permissions: MutableSet<Permission>): Role {
        val roleResult = roleRepository.findByName(name)
        val role = if (roleResult.isPresent) {
            val r = roleResult.get()
            r.permissions = permissions
            r
        } else Role(name, permissions)
        return roleRepository.save(role)
    }

    /**
     * Create a user or update it if it exists.
     *
     * @param firstName First name
     * @param lastName Last name
     * @param username Username
     * @param password Password
     * @param roles The roles the user will have assigned to them
     * @return The user created/updated
     */
    private fun createOrUpdateUser(firstName: String, lastName: String, username: String, password: String,
                                   roles: MutableSet<Role>): ApplicationUser {
        val credential = createOrUpdateCredential(username, password)
        val userResult = userRepository.findByCredential(credential)
        val user = if (userResult.isPresent) {
            val u = userResult.get()
            u.firstName = firstName
            u.lastName = lastName
            u.roles = roles
            return u
        } else ApplicationUser(firstName, lastName, credential, roles)
        return userRepository.save(user)
    }

    /**
     * Create a credential or update it if it exists.
     *
     * @param username Username
     * @param password Password
     * @return The credential created/updated
     */
    private fun createOrUpdateCredential(username: String, password: String): Credential {
        val credentialResult = credentialRepository.findByUsername(username)
        val encodedPassword = passwordEncoder.encode(password)
        val credential = if (credentialResult.isPresent) {
            val c = credentialResult.get()
            c.password = encodedPassword
            return c
        } else Credential(username, encodedPassword)
        return credentialRepository.save(credential)
    }

}