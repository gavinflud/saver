package com.gavinflood.saver.helper.builder

import com.gavinflood.saver.domain.AccountType

class AccountTypeBuilder(private val code: String = "account", private val name: String = "Current Account",
                         private val description: String = "Current Account") : BaseBuilder<AccountType>() {

    override fun build(): AccountType {
        return AccountType(code, name, description)
    }

}