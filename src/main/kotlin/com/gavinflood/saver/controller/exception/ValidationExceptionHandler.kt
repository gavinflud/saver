package com.gavinflood.saver.controller.exception

import com.gavinflood.saver.domain.exception.ServiceValidationException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.transaction.TransactionSystemException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import javax.validation.ConstraintViolationException

/**
 * Defines how to respond when validation errors are thrown.
 */
@ControllerAdvice
class ValidationExceptionHandler : ResponseEntityExceptionHandler() {

    /**
     * Handle [TransactionSystemException] occurrences. These can wrap a [ConstraintViolationException] as the
     * application is configured to be transactional.
     *
     * @param exception The exception that was thrown
     * @return A ResponseEntity listing the validation errors
     */
    @ExceptionHandler(value = [TransactionSystemException::class])
    fun handleTransactionSystemException(exception: TransactionSystemException)
            : ResponseEntity<ValidationResponse> {
        val rootException = exception.rootCause
        val status = HttpStatus.UNPROCESSABLE_ENTITY.value()
        val validationResponse = ValidationResponse(status)

        // If the root is a ConstraintViolationException then populate the response validation errors accordingly
        if (rootException is ConstraintViolationException) {
            rootException.constraintViolations
                    .forEach { violation ->
                        validationResponse.validationErrors[violation.propertyPath.toString()] = violation.message
                    }
        }

        return ResponseEntity.status(status).body(validationResponse)
    }

    /**
     * Handle [ServiceValidationException] occurrences to return the validation errors raised.
     *
     * @param exception The exception
     * @return A ResponseEntity listing the validation errors
     */
    @ExceptionHandler(value = [ServiceValidationException::class])
    fun handleServiceValidationException(exception: ServiceValidationException): ResponseEntity<ValidationResponse> {
        val status = HttpStatus.UNPROCESSABLE_ENTITY.value()
        val validationResponse = ValidationResponse(status)
        validationResponse.validationErrors.putAll(exception.errors.mapKeys { entry -> entry.key.name })
        return ResponseEntity.status(status).body(validationResponse)
    }

    /**
     * Override the [handleMethodArgumentNotValid] function to return the field errors stored under the bindingResult.
     *
     * @param exception The exception
     * @param headers The headers to be written to the response
     * @param status The selected response status
     * @param request The current request
     */
    override fun handleMethodArgumentNotValid(exception: MethodArgumentNotValidException, headers: HttpHeaders,
                                              status: HttpStatus, request: WebRequest): ResponseEntity<Any> {
        val bindingResult = exception.bindingResult
        val httpStatus = HttpStatus.UNPROCESSABLE_ENTITY.value()
        val validationResponse = ValidationResponse(httpStatus)
        bindingResult.fieldErrors
                .forEach { error ->
                    run {
                        val message = error.defaultMessage
                        if (message != null) {
                            validationResponse.validationErrors[error.field] = message
                        }
                    }
                }
        return ResponseEntity.status(httpStatus).body(validationResponse)
    }

}

/**
 * Data class to store a validation response for a request.
 *
 * @param status The HTTP Status code
 * @param validationErrors A map of validation errors with the property as the key and the message as the value
 */
data class ValidationResponse(val status: Int, val validationErrors: MutableMap<String, String> = mutableMapOf())