package com.gavinflood.saver.helper.builder

import com.gavinflood.saver.domain.Account

class AccountBuilder(private var name: String = "Current Account", private var accountTypeBuilder: AccountTypeBuilder)
    : BaseBuilder<Account>() {

    fun withName(name: String): AccountBuilder {
        this.name = name
        return this
    }

    fun withAccountType(accountTypeBuilder: AccountTypeBuilder): AccountBuilder {
        this.accountTypeBuilder = accountTypeBuilder
        return this
    }

    override fun build(): Account {
        return Account(name, accountTypeBuilder.build())
    }

}