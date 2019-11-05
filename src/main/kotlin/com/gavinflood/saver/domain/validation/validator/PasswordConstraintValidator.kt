package com.gavinflood.saver.domain.validation.validator

import com.gavinflood.saver.domain.validation.annotation.ValidPassword
import org.passay.LengthRule
import org.passay.PasswordData
import org.passay.PasswordValidator
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

/**
 * Validates a password.
 */
class PasswordConstraintValidator : ConstraintValidator<ValidPassword, String> {

    /**
     * Checks if the password is valid based on the rules in place.
     *
     * @param password The password to validate
     * @param context Context in which the constraint is evaluated
     * @return True if the password is valid, false otherwise
     */
    override fun isValid(password: String?, context: ConstraintValidatorContext?): Boolean {
        val validator = PasswordValidator(listOf(
                LengthRule(12, Integer.MAX_VALUE)
        ))

        val result = validator.validate(PasswordData(password))
        return if (result.isValid) {
            true
        } else {
            context?.disableDefaultConstraintViolation()
            context?.buildConstraintViolationWithTemplate(validator.getMessages(result).joinToString(","))
                    ?.addConstraintViolation()
            return false
        }
    }

}