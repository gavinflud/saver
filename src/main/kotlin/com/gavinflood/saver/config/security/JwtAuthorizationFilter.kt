package com.gavinflood.saver.config.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.TokenExpiredException
import com.gavinflood.saver.config.Properties
import com.gavinflood.saver.controller.exception.ValidationResponse
import com.google.gson.GsonBuilder
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import java.io.IOException
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

    private val _logger = LoggerFactory.getLogger(JwtAuthorizationFilter::class.java)

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

        val authenticationToken = getAuthentication(request, response) ?: return
        SecurityContextHolder.getContext().authentication = authenticationToken
        filterChain.doFilter(request, response)
    }

    /**
     * Get the authentication token from the header if it exists.
     *
     * @param request HTTP request
     * @return The authentication token
     */
    private fun getAuthentication(request: HttpServletRequest, response: HttpServletResponse): JwtAuthenticationToken? {
        val logPrefix = "#getAuthentication ::"
        val token = request.getHeader(properties.header)
        if (token != null) {
            try {
                _logger.debug("$logPrefix Attempting to validate token '$token'")
                val user = JWT.require(Algorithm.HMAC512(properties.secret))
                        .build()
                        .verify(token.replace(properties.tokenPrefix, "").trim())
                        .subject

                return if (user != null) {
                    JwtAuthenticationToken(user)
                } else null
            } catch (exception: TokenExpiredException) {
                // Send a custom response if the authentication token has expired
                _logger.debug("$logPrefix ${exception.message} ($token)")
                val status = HttpStatus.FORBIDDEN
                setTokenExpiredResponse(response, status)
            }
        }

        return null
    }

    /**
     * Customize the response when the authentication token has expired.
     *
     * @param response The HTTP response
     * @param status The HTTP status code to send
     */
    private fun setTokenExpiredResponse(response: HttpServletResponse, status: HttpStatus) {
        response.status = status.value()
        response.contentType = "application/json"
        val body = ValidationResponse(status.value(), mutableMapOf("authorizationToken" to "Token expired"))
        try {
            val writer = response.writer
            val gson = GsonBuilder().setPrettyPrinting().create()
            writer.write(gson.toJson(body))
        } catch (exception: IOException) {
            _logger.error("#setTokenExpiredResponse :: ", exception)
        }
    }

}