package com.gavinflood.saver.repository

import com.gavinflood.saver.domain.Account
import com.gavinflood.saver.domain.Transaction
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

/**
 * Interface for operations on the [Transaction] repository.
 */
@Repository
interface TransactionRepository : BaseRepository<Transaction> {

    /**
     * Find all transactions associated with an account sorted from newest to oldest.
     *
     * @param account The account the transactions are associated with
     * @param accountTransferredTo The account that any transactions were transferred to (should be the same as account)
     * @param pageable Defines the paging structure
     * @return The found transactions, if any
     */
    fun findAllByAccountOrAccountTransferredToOrderByDateDesc(account: Account, accountTransferredTo: Account,
                                                              pageable: Pageable): Page<Transaction>

}