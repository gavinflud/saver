package com.gavinflood.saver.helper.builder

import com.gavinflood.saver.config.constants.TypeConstants
import com.gavinflood.saver.domain.AccountType
import com.gavinflood.saver.domain.Type

class AccountTypeBuilder(group: String = TypeConstants.getGroup(AccountType::class),
                         code: String = TypeConstants.ACCOUNT_CURRENT, name: String = "Current Account",
                         description: String = "Current Account") : TypeBuilder(group, code, name, description) {

    override fun build(): Type {
        return AccountType(code, name, description)
    }

}