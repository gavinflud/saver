package com.gavinflood.saver.domain.validation.exception

import kotlin.reflect.KProperty1

/**
 * Represents one or more validation errors that were found while validating at the service level (or outside of
 * hibernate-native validation).
 */
class ServiceValidationException() : RuntimeException("Service Validation Exception") {

    val errors = mutableMapOf<KProperty1<*, *>, String>()

    /**
     * Constructor.
     *
     * @param errors The key is the property that is invalid, the value is a message explaining the issue
     */
    constructor(errors: MutableMap<KProperty1<*, *>, String>) : this() {
        errors.putAll(errors)
    }

}