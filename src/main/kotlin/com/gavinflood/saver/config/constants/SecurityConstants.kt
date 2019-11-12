package com.gavinflood.saver.config.constants

/**
 * Security constants.
 */
class SecurityConstants {

    companion object {

        private const val ROLE_PREFIX = "ROLE_"

        const val PERMISSION_ADMIN = "ADMIN"
        const val PERMISSION_DEFAULT = "DEFAULT"

        const val ROLE_ADMIN = "ADMIN"
        const val ROLE_USER = "USER"

        /**
         * Generates the GrantedAuthority name based on the permission name. This appends the permission name to the
         * ROLE_ prefix.
         *
         * @return The authority name based on the permission name.
         */
        fun getAuthorityName(permissionName: String): String {
            return ROLE_PREFIX + permissionName
        }

    }

}