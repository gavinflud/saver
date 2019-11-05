package com.gavinflood.saver.domain

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.User

/**
 * A simple extension of the default Spring [User] that just adds an ID property to it.
 *
 * @param id Identifies the user
 * @param username The username presented to the <code>DaoAuthenticationProvider</code>
 * @param password The password that should be presented to the <code>DaoAuthenticationProvider</code>
 * @param authorities The authorities that should be granted to the caller if they presented the correct username and
 * password and the user is enabled.
 */
class SimpleUserDetails(val id: Long, username: String, password: String,
                        authorities: Collection<GrantedAuthority>) : User(username, password, authorities)