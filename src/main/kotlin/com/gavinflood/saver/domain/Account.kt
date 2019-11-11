package com.gavinflood.saver.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.ManyToMany


/**
 * An account can be owned by one or more users. Transactions sit under an account.
 *
 * An example would be a checking account. Multiple people can co-own a checking account. As money is lodged into the
 * account and payments are made from it, these would register as transactions under it.
 */
@Entity(name = "gf_account")
class Account(

        /**
         * The account name.
         */
        @Column(name = "gf_name")
        private var name: String,

        /**
         * The users that have access to the account.
         */
        @ManyToMany(mappedBy = "accounts")
        @JsonIgnore
        private var users: Set<ApplicationUser> = mutableSetOf()

) : IdentifiableEntity() {

        
}