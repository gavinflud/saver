package com.gavinflood.saver.config.data

import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.transaction.annotation.EnableTransactionManagement

/**
 * Database configuration
 */
@Configuration
@EnableJpaRepositories("com.gavinflood.saver")
@EnableTransactionManagement
class DataConfig