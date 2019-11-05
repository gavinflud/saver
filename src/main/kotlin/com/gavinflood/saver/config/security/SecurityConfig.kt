package com.gavinflood.saver.config.security

import com.gavinflood.saver.config.Properties
import com.gavinflood.saver.service.SimpleUserDetailsService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import java.util.Collections.singletonList


/**
 * Security configuration for the application.
 *
 * @param userDetailsService User details service implementation
 * @param passwordEncoder Password encoder
 * @param properties The custom properties wrapper for the application
 * @param jwtAuthenticationProvider JWT authentication provider to retrieve user details
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class SecurityConfig(val userDetailsService: SimpleUserDetailsService,
                     val passwordEncoder: PasswordEncoder, val properties: Properties,
                     val jwtAuthenticationProvider: JwtAuthenticationProvider) : WebSecurityConfigurerAdapter() {

    /**
     * Configure HTTP security.
     *
     * @param httpSecurity The HTTP security to modify
     */
    override fun configure(httpSecurity: HttpSecurity) {
        httpSecurity
                .cors()
                .and().csrf().disable()
                .authorizeRequests()
                .mvcMatchers(HttpMethod.POST, properties.registerUrl).permitAll()
                .mvcMatchers(HttpMethod.POST, properties.loginUrl).permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(JwtAuthenticationTokenFilter(authenticationManager(), properties),
                        UsernamePasswordAuthenticationFilter::class.java)
                .addFilterBefore(JwtAuthorizationFilter(authenticationManager(), properties),
                        UsernamePasswordAuthenticationFilter::class.java)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    }

    /**
     * Specifies the authentication manager.
     *
     * @param authenticationManagerBuilder The authentication manager builder to use
     */
    override fun configure(authenticationManagerBuilder: AuthenticationManagerBuilder) {
        authenticationManagerBuilder
                .authenticationProvider(jwtAuthenticationProvider)
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder)
    }

    /**
     * @return A source wrapper that provides a Cors configuration instance
     */
    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration()
        configuration.allowedOrigins = singletonList(CorsConfiguration.ALL)
        configuration.allowedMethods = singletonList(CorsConfiguration.ALL)
        configuration.allowedHeaders = singletonList(CorsConfiguration.ALL)
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }
}
