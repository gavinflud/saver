package com.gavinflood.saver.domain

import com.gavinflood.saver.config.constants.TypeConstants
import javax.persistence.Column
import javax.persistence.Entity

/**
 * A type is essentially a persisted enum, with a name and description for the value. It must be extended for each
 * type that is needed.
 */
@Entity(name = "gf_type")
open class Type(

        /**
         * Groups related types together. This should be populated by the subclass and always remain the same for
         * instances of that type.
         *
         * For example, if you take the [AccountType] class, then "account" will be the group for all instances of that
         * class.
         */
        @Column(name = "gf_group")
        var group: String,

        /**
         * Easily identifies the type. This should be unique to the group.
         */
        @Column(name = "gf_code")
        var code: String,

        /**
         * Type name.
         */
        @Column(name = "gf_name")
        var name: String,

        /**
         * Type description
         */
        @Column(name = "gf_description")
        var description: String

) : IdentifiableEntity()

/**
 * Identifies the type of an account (e.g. checking account, credit card, etc.)
 */
class AccountType(code: String, name: String, description: String)
    : Type(TypeConstants.getGroup(AccountType::class), code, name, description)