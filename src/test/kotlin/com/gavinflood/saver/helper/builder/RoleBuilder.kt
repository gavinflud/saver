package com.gavinflood.saver.helper.builder

import com.gavinflood.saver.config.constants.SecurityConstants
import com.gavinflood.saver.domain.Role

class RoleBuilder(private var name: String = SecurityConstants.ROLE_USER) : BaseBuilder<Role>() {

    private val permissionBuilders = mutableListOf<PermissionBuilder>()

    fun withName(_name: String): RoleBuilder {
        name = _name
        return this
    }

    fun withPermissions(_permissionBuilders: MutableList<PermissionBuilder>): RoleBuilder {
        permissionBuilders.clear()
        permissionBuilders.addAll(_permissionBuilders)
        return this
    }

    fun withPermission(permissionBuilder: PermissionBuilder): RoleBuilder {
        permissionBuilders.add(permissionBuilder)
        return this
    }

    override fun build(): Role {
        val role = Role(name)
        role.permissions = permissionBuilders
                .map { permissionBuilder -> permissionBuilder.build() }
                .toMutableSet()
        return role
    }

}