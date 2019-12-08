package com.gavinflood.saver.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*


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
        var name: String,

        /**
         * The type of the account.
         */
        @ManyToOne
        @JoinColumn(name = "gf_account_type_id")
        var accountType: AccountType

) : IdentifiableEntity() {

    /**
     * The users that have access to the account.
     */
    @ManyToMany(mappedBy = "accounts", cascade = [CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH])
    @JsonIgnore
    var users: MutableSet<ApplicationUser> = mutableSetOf()

    /**
     * Constructor that will mostly be called through Jackson from REST API calls (since the users don't have to be
     * passed in the payload).
     *
     * @param name The account name
     * @param accountType The type of the account
     * @param _users The users that have access to the account
     */
    constructor(name: String, accountType: AccountType, _users: MutableSet<ApplicationUser>) : this(name, accountType) {
        users = _users
    }

}