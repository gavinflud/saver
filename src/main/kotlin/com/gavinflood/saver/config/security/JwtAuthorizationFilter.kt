package com.gavinflood.saver.config.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.gavinflood.saver.config.Properties
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Processes a HTTP request's JWT authorization headers, putting the result into the [SecurityContextHolder].
 *
 * @param authManager Processes the authentication request
 * @param properties The custom properties wrapper for the application
 */
class JwtAuthorizationFilter(authManager: AuthenticationManager,
                             private val properties: Properties) : BasicAuthenticationFilter(authManager) {

    /**
     * Processes the authorization request header.
     *
     * @param request HTTP request
     * @param response HTTP response
     * @param filterChain Used to invoke the next filter
     */
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse,
                                  filterChain: FilterChain) {
        val header = request.getHeader(properties.header)

        if (header == null || !header.startsWith(properties.tokenPrefix)) {
            filterChain.doFilter(request, response)
            return
        }

        val authenticationToken = getAuthentication(request)
        SecurityContextHolder.getContext().authentication = authenticationToken
        filterChain.doFilter(request, response)
    }

    /**
     * Get the authentication token from the header if it exists.
     *
     * @param request HTTP request
     * @return The authentication token
     */
    private fun getAuthentication(request: HttpServletRequest): JwtAuthenticationToken? {
        val token = request.getHeader(properties.header)
        if (token != null) {
            val user = JWT.require(Algorithm.HMAC512(properties.secret))
                    .build()
                    .verify(token.replace(properties.tokenPrefix, "").trim())
                    .subject

            return if (user != null) {
                JwtAuthenticationToken(user)
            } else null
        }

        return null
    }

}