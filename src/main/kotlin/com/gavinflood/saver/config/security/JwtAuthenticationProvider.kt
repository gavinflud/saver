package com.gavinflood.saver.config.security

import com.gavinflood.saver.service.SimpleUserDetailsService
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

/**
 * Custom JWT authentication provider that is needed to avoid calling
 * [#additionalAuthenticationChecks(UserDetails, UsernamePasswordAuthenticationToken)] on a JWT authentication token.
 * If this is called it will fail since no password is stored on the token instance.
 *
 * @param userDetailsService Service used to retrieve a user
 * @param passwordEncoder Password encoder
 */
@Component
class JwtAuthenticationProvider(userDetailsService: SimpleUserDetailsService,
                                passwordEncoder: PasswordEncoder) : DaoAuthenticationProvider() {

    init {
        this.userDetailsService = userDetailsService
        this.passwordEncoder = passwordEncoder
    }

    /**
     * If dealing with a JwtAuthenticationToken then do not perform any additional checks. However, in all other
     * normal scenarios the additional checks can be performed.
     *
     * @param userDetails User being evaluated
     * @param authenticationToken Authentication token instance
     */
    override fun additionalAuthenticationChecks(userDetails: UserDetails,
                                                authenticationToken: UsernamePasswordAuthenticationToken) {
        if (authenticationToken !is JwtAuthenticationToken) {
            super.additionalAuthenticationChecks(userDetails, authenticationToken)
        }
    }

}