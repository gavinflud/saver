package com.gavinflood.saver.domain.validation.annotation

import com.gavinflood.saver.domain.validation.validator.PasswordConstraintValidator
import javax.validation.Constraint
import kotlin.reflect.KClass

/**
 * Annotation to validate a password.
 */
@Constraint(validatedBy = [PasswordConstraintValidator::class])
@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class ValidPassword(
        val message: String = "Invalid Password",
        val groups: Array<KClass<out Any>> = [],
        val payload: Array<KClass<out Any>> = []
)