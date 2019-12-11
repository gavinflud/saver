package com.gavinflood.saver.domain

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

/**
 * Categorizes transactions. Can be general and available to all users, or created by and available to a single user.
 */
@Entity
class TransactionCategory(

        /**
         * Unique code identifying the transaction category.
         */
        @Column(name = "gf_code", unique = true)
        var code: String,

        /**
         * The category name.
         */
        @Column(name = "gf_name")
        var name: String,

        /**
         * Describes this transaction category.
         */
        @Column(name = "gf_description")
        var description: String

) : IdentifiableEntity() {

    /**
     * The user that this category belongs to. If null, this category is available to all users.
     */
    @ManyToOne
    @JoinColumn(name = "gf_user_id")
    var user: ApplicationUser? = null

}