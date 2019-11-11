package com.gavinflood.saver.service

import com.gavinflood.saver.BaseTest
import com.gavinflood.saver.config.security.constant.Constants
import com.gavinflood.saver.domain.ApplicationUser
import com.gavinflood.saver.helper.builder.RoleBuilder
import com.gavinflood.saver.repository.ApplicationUserRepository
import com.gavinflood.saver.repository.CredentialRepository
import com.gavinflood.saver.repository.RoleRepository
import com.nhaarman.mockitokotlin2.whenever
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.AdditionalAnswers.returnsFirstArg
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import java.util.*

@ExtendWith(MockitoExtension::class)
class ApplicationUserServiceTest : BaseTest() {

    @Mock
    private lateinit var userRepository: ApplicationUserRepository

    @Mock
    private lateinit var roleRepository: RoleRepository

    @Mock
    private lateinit var credentialRepository: CredentialRepository

    private lateinit var userService: ApplicationUserService
    private val passwordEncoder = BCryptPasswordEncoder()

    @BeforeEach
    fun beforeEach() {
        userService = ApplicationUserService(userRepository, credentialRepository, roleRepository, passwordEncoder)
    }

    @Test
    fun testCreateAssignsDefaultUserRole() {
        whenever(credentialRepository.findByUsername(anyString())).thenReturn(Optional.empty())
        whenever(roleRepository.findByName(Constants.ROLE_USER)).thenReturn(Optional.of(RoleBuilder().build()))
        whenever(userRepository.save(any<ApplicationUser>())).then(returnsFirstArg<ApplicationUser>())

        var user = createDefaultApplicationUser()
        assertTrue(user.roles.isEmpty())

        user = userService.create(user)
        assertTrue(user.roles.any { role -> role.name == Constants.ROLE_USER })
    }

}