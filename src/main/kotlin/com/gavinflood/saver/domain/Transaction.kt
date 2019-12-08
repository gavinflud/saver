package com.gavinflood.saver.domain

import java.math.BigDecimal
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity(name = "gf_transaction")
class Transaction(

        /**
         * Describes the transaction.
         */
        @Column(name = "gf_description")
        var description: String,

        /**
         * The transaction amount.
         */
        @Column(name = "gf_amount")
        var amount: BigDecimal,

        /**
         * The account the transaction took place on.
         */
        @ManyToOne
        @JoinColumn(name = "gf_account_id")
        var account: Account,

        /**
         * The date the transaction took place.
         */
        @Column(name = "gf_date")
        var date: Date


) : IdentifiableEntity() {


}