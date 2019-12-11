package com.gavinflood.saver.domain

import javax.persistence.Column
import javax.persistence.Entity

/**
 * Categorizes an account based on the type of account it is.
 */
@Entity(name = "gf_account_type")
class AccountType(

        /**
         * Unique code identifying the account type.
         */
        @Column(name = "gf_code", unique = true)
        var code: String,

        /**
         * The name of the account type.
         */
        @Column(name = "gf_name")
        var name: String,

        /**
         * Describes this type of account.
         */
        @Column(name = "gf_description")
        var description: String

) : IdentifiableEntity()