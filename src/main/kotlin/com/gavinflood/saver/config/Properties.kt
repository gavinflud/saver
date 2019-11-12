package com.gavinflood.saver.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource

/**
 * Wrapper for the custom application properties.
 */
@Configuration
@PropertySource("classpath:configuration.properties")
class Properties {

    /**
     * The expiration time for a JWT token
     */
    @Value("\${saver.jwt.expiration}")
    val expirationTime: Long = 10800000

    /**
     * The secret for a JWT token
     */
    @Value("\${saver.jwt.secret}")
    lateinit var secret: String

    /**
     * The header key for a JWT token
     */
    @Value("\${saver.jwt.header}")
    lateinit var header: String

    /**
     * The header value prefix for a JWT token
     */
    @Value("\${saver.jwt.prefix}")
    lateinit var tokenPrefix: String

    /**
     * The URL to redirect a user to to register
     */
    @Value("\${saver.jwt.register.url}")
    lateinit var registerUrl: String

    /**
     * The URL to redirect a user to to login
     */
    @Value("\${saver.jwt.login.url}")
    lateinit var loginUrl: String

    /**
     * The initial password when creating the admin user
     */
    @Value("\${saver.preload.user.admin.password}")
    lateinit var adminPassword: String

}