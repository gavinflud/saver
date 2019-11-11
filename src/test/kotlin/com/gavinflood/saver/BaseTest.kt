package com.gavinflood.saver

import com.gavinflood.saver.domain.ApplicationUser
import com.gavinflood.saver.helper.builder.ApplicationUserBuilder
import com.gavinflood.saver.helper.builder.CredentialBuilder
import javax.validation.Validation
import javax.validation.Validator

abstract class BaseTest {

    protected val validator: Validator = Validation.buildDefaultValidatorFactory().validator

    protected fun createDefaultApplicationUser(): ApplicationUser {
        return ApplicationUserBuilder(credentialBuilder = CredentialBuilder()).build()
    }

}