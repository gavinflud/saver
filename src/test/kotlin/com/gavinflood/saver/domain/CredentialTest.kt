package com.gavinflood.saver.domain

import com.gavinflood.saver.BaseTest
import com.gavinflood.saver.helper.builder.CredentialBuilder
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class CredentialTest : BaseTest() {

    @Test
    fun testPasswordWithLessThan12CharactersFailsValidation() {
        val credential = CredentialBuilder(password = "1234").build()
        val violations = validator.validate(credential)
        assertTrue(violations.size > 0)
    }

    @Test
    fun testPasswordWithMoreThan11CharactersPassesValidation() {
        val credential = CredentialBuilder(password = "123456789012").build()
        val violations = validator.validate(credential)
        assertTrue(violations.isEmpty())
    }

}