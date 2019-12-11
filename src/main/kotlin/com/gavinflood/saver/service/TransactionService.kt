package com.gavinflood.saver.service

import com.gavinflood.saver.domain.Account
import com.gavinflood.saver.domain.Transaction
import com.gavinflood.saver.repository.TransactionRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

/**
 * Business logic for a [Transaction].
 *
 * @param repository The repository to interact with the database layer
 */
@Service
class TransactionService(repository: TransactionRepository)
    : BaseService<Transaction, TransactionRepository>(repository) {

    /**
     * Find all transactions related to an account.
     *
     * @param account The account the transactions belong to
     * @param pageable Defines the paging structure
     * @return The found transactions, if any
     */
    fun findAllForAccount(account: Account, pageable: Pageable): Page<Transaction> {
        return repository.findAllByAccountOrAccountTransferredToOrderByDateDesc(account, account, pageable)
    }

}