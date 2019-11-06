package com.gavinflood.saver

import com.gavinflood.saver.domain.ApplicationUser
import com.gavinflood.saver.helper.builder.ApplicationUserBuilder
import com.gavinflood.saver.helper.builder.CredentialBuilder
import javax.validation.Validation

abstract class BaseTest {

    protected val validator = Validation.buildDefaultValidatorFactory().validator

    protected fun createDefaultApplicationUser(): ApplicationUser {
        return ApplicationUserBuilder(credentialBuilder = CredentialBuilder()).build()
    }

}