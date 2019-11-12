package com.gavinflood.saver.config.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm.HMAC512
import com.fasterxml.jackson.databind.ObjectMapper
import com.gavinflood.saver.config.Properties
import com.gavinflood.saver.domain.Credential
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.User
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import java.io.IOException
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Processes an authentication request, returning an HTTP header with a JWT if successfully authenticated.
 *
 * @param authManager Authentication manager implementation
 * @param properties The custom properties wrapper for the application
 */
class JwtAuthenticationTokenFilter(
        private val authManager: AuthenticationManager,
        private val properties: Properties
) : AbstractAuthenticationProcessingFilter(AntPathRequestMatcher(properties.loginUrl)) {

    /**
     * Attempt to authenticate a request.
     *
     * @param request HTTP request
     * @param response HTTP response
     * @return An authentication token if successful
     */
    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication {
        try {
            val credential = ObjectMapper().readValue(request.inputStream, Credential::class.java)
            return authManager.authenticate(
                    UsernamePasswordAuthenticationToken(credential.username, credential.password)
            )
        } catch (exception: IOException) {
            throw RuntimeException(exception)
        }
    }

    /**
     * Default behaviour on successful authentication.
     *
     * @param request HTTP request
     * @param response HTTP response
     * @param filterChain Used to invoke the next filter
     * @param authentication Authentication token storing the username
     */
    override fun successfulAuthentication(request: HttpServletRequest, response: HttpServletResponse,
                                          filterChain: FilterChain, authentication: Authentication) {
        val token = JWT.create()
                .withSubject((authentication.principal as User).username)
                .withExpiresAt(Date(System.currentTimeMillis() + properties.expirationTime))
                .sign(HMAC512(properties.secret.toByteArray()))
        response.addHeader(properties.header, properties.tokenPrefix + " " + token)
    }

}