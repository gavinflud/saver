package com.gavinflood.saver.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import java.math.BigDecimal
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

/**
 * A transaction is either an income or expenditure item. It must be associated with at least one account.
 */
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
        @JsonIgnore
        var account: Account,

        /**
         * The date the transaction took place.
         */
        @Column(name = "gf_date")
        var date: Date,

        /**
         * The category the transaction belongs to.
         */
        @ManyToOne
        @JoinColumn(name = "gf_transaction_category_id")
        var category: TransactionCategory

) : IdentifiableEntity() {

    /**
     * If an amount was transferred from one account to another, then this represents the receiving account. This
     * should show as an expenditure transaction on the originating account and an income transaction on the receiving
     * account.
     */
    @ManyToOne
    @JoinColumn(name = "gf_account_transferred_to_id")
    var accountTransferredTo: Account? = null

}