package com.gavinflood.saver.domain.response

import org.springframework.http.ResponseEntity

/**
 * Send an OK response with a resource as the body.
 */
fun <T : Any> sendOkResponse(resource: T): ResponseEntity<T> {
    return ResponseEntity.ok(resource)
}