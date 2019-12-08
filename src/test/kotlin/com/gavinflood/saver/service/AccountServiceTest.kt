package com.gavinflood.saver.service

import com.gavinflood.saver.BaseTest
import com.gavinflood.saver.domain.Account
import com.gavinflood.saver.helper.builder.AccountBuilder
import com.gavinflood.saver.helper.builder.AccountTypeBuilder
import com.gavinflood.saver.repository.AccountRepository
import com.gavinflood.saver.repository.AccountTypeRepository
import com.nhaarman.mockitokotlin2.whenever
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.AdditionalAnswers.returnsFirstArg
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.anyLong
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import java.util.*

@ExtendWith(MockitoExtension::class)
class AccountServiceTest : BaseTest() {

    @Mock
    private lateinit var accountRepository: AccountRepository

    @Mock
    private lateinit var accountTypeRepository: AccountTypeRepository

    @Mock
    private lateinit var securityService: SecurityService

    private lateinit var accountService: AccountService

    @BeforeEach
    fun beforeEach() {
        accountService = AccountService(accountRepository, accountTypeRepository, securityService)
    }

    @Test
    fun testCreateAssignsCurrentUser() {
        val currentUser = createDefaultApplicationUser()
        whenever(securityService.getCurrentUser()).thenReturn(Optional.of(currentUser))
        whenever(accountTypeRepository.findById(anyLong())).thenReturn(Optional.of(AccountTypeBuilder().build()))
        whenever(accountRepository.save(any(Account::class.java))).then(returnsFirstArg<Account>())

        val account = AccountBuilder(accountTypeBuilder = AccountTypeBuilder()).build()
        assertTrue(account.users.isEmpty())

        accountService.create(account)
        assertTrue(account.users.contains(currentUser))
    }

}