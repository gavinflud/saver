package com.gavinflood.saver.helper.builder

import com.gavinflood.saver.domain.Credential

class CredentialBuilder(private var username: String = "jbloggs", private var password: String = "password")
    : BaseBuilder<Credential>() {

    fun withUsername(_username: String): CredentialBuilder {
        username = _username
        return this
    }

    fun withPassword(_password: String): CredentialBuilder {
        password = _password
        return this
    }

    override fun build(): Credential {
        return Credential(username, password)
    }

}