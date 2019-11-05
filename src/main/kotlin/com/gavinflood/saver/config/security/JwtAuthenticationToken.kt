package com.gavinflood.saver.config.security

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.authentication.dao.DaoAuthenticationProvider

/**
 * Authentication implementation that is needed to be able to authenticate a user who is solely passing a JWT. Without
 * this implementation, the []UsernamePasswordAuthenticationToken] would be used but no password exists on this
 * when a JWT is passed as a header, so a failure occurs in [DaoAuthenticationProvider] when calling
 * 'additionalAuthenticationChecks'.
 *
 * @param principal Stores the username
 */
class JwtAuthenticationToken(principal: Any) : UsernamePasswordAuthenticationToken(principal, null)