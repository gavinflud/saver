package com.gavinflood.saver.service

import com.gavinflood.saver.domain.Account
import com.gavinflood.saver.domain.ApplicationUser
import com.gavinflood.saver.domain.dto.AccountDTO
import com.gavinflood.saver.repository.AccountRepository
import com.gavinflood.saver.repository.AccountTypeRepository
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.security.access.AccessDeniedException
import org.springframework.stereotype.Service

/**
 * Business logic for an [Account].
 *
 * @param repository The repository to interact with the database layer
 * @param accountTypeRepository Repository for the AccountType entity
 * @param securityService Service that provides security functionality
 */
@Service
class AccountService(repository: AccountRepository, val accountTypeRepository: AccountTypeRepository,
                     val securityService: SecurityService)
    : BaseService<Account, AccountRepository>(repository) {

    private val logger = LoggerFactory.getLogger(AccountService::class.java)

    /**
     * Create a new account.
     *
     * @param resource The account to be created
     */
    override fun create(resource: Account): Account {
        val currentUser = securityService.getCurrentUser().orElseThrow()
        currentUser.accounts.add(resource)
        resource.users.add(currentUser)
        return super.create(resource)
    }

    /**
     * Find all accounts for the current user.
     *
     * @param pageable Defines the paging structure for the result
     * @return A page of accounts
     */
    override fun findAll(pageable: Pageable): Page<Account> {
        val currentUser = securityService.getCurrentUser().orElseThrow()
        return findAllForUser(currentUser, pageable)
    }

    /**
     * Find all accounts for a specified user.
     *
     * @param user The user that owns the accounts
     * @param pageable Defines the paging structure for the result
     * @return A page of accounts
     */
    fun findAllForUser(user: ApplicationUser, pageable: Pageable): Page<Account> {
        return repository.findByUsersContainsOrderByName(user, pageable)
    }

    /**
     * Find one account.
     *
     * The current user must have the authority to see the account in order to return it.
     *
     * @param id Identifies the user
     * @return The found account
     */
    override fun findOne(id: Long): Account {
        val logPrefix = "#findOne ::"
        val currentUser = securityService.getCurrentUser().orElseThrow()
        val account = super.findOne(id)
        if (account.users.contains(currentUser) || currentUser.isAdmin()) {
            return account
        }

        logger.warn("$logPrefix User '${currentUser.id}' does not have access to account '$id'")
        throw AccessDeniedException("You do not have access to view that account")
    }

    /**
     * Update an existing account.
     *
     * @param id Identifies the account
     * @param dto DTO containing the account changes
     * @return The persisted account with the changes intact
     */
    fun update(id: Long, dto: AccountDTO): Account {
        val account = findOne(id)
        val accountType = accountTypeRepository.findById(dto.accountTypeId)
        account.accountType = accountType.orElseThrow()
        account.name = dto.name
        return super.update(account)
    }

}