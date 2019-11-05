package com.gavinflood.saver.domain

import com.gavinflood.saver.helper.builder.ApplicationUserBuilder
import com.gavinflood.saver.helper.builder.CredentialBuilder
import com.gavinflood.saver.helper.builder.PermissionBuilder
import com.gavinflood.saver.helper.builder.RoleBuilder
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ApplicationUserTest {

    @Test
    fun testGetFullNameReturnsBothNamesConcatenated() {
        val firstName = "Test"
        val lastName = "User"
        val user = ApplicationUserBuilder(firstName, lastName, CredentialBuilder()).build()
        assertEquals(user.getFullName(), "$firstName $lastName")
    }

    @Test
    fun testGetAuthoritiesReturnsEmptySetWhenNoRolesExist() {
        val user = ApplicationUserBuilder(credentialBuilder = CredentialBuilder()).build()
        assertEquals(user.getAuthorities().size, 0)
    }

    @Test
    fun testGetAuthoritiesReturnsPopulatedSetWhenRolesExist() {
        val user = ApplicationUserBuilder(credentialBuilder = CredentialBuilder())
                .withRole(RoleBuilder()
                        .withPermission(PermissionBuilder("PERMISSION1"))
                        .withPermission(PermissionBuilder("PERMISSION2")))
                .build()
        assertEquals(user.getAuthorities().size, 2)
    }

}