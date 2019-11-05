package com.gavinflood.saver.helper.builder

import com.gavinflood.saver.domain.ApplicationUser

class ApplicationUserBuilder(private var firstName: String = "Joe", private var lastName: String = "Bloggs",
                             private var credentialBuilder: CredentialBuilder)
    : BaseBuilder<ApplicationUser>() {

    private val roleBuilders = mutableListOf<RoleBuilder>()

    fun withFirstName(_firstName: String): ApplicationUserBuilder {
        firstName = _firstName
        return this
    }

    fun withLastName(_lastName: String): ApplicationUserBuilder {
        lastName = _lastName
        return this
    }

    fun withCredential(_credentialBuilder: CredentialBuilder): ApplicationUserBuilder {
        credentialBuilder = _credentialBuilder
        return this
    }

    fun withRoles(_roleBuilders: MutableList<RoleBuilder>): ApplicationUserBuilder {
        roleBuilders.clear()
        roleBuilders.addAll(_roleBuilders)
        return this
    }

    fun withRole(roleBuilder: RoleBuilder): ApplicationUserBuilder {
        roleBuilders.add(roleBuilder)
        return this
    }

    override fun build(): ApplicationUser {
        val user = ApplicationUser(firstName, lastName, credentialBuilder.build())
        user.roles = roleBuilders.map { roleBuilder -> roleBuilder.build() }.toMutableSet()
        return user
    }

}