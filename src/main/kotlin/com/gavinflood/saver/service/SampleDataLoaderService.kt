package com.gavinflood.saver.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.gavinflood.saver.domain.Account
import com.gavinflood.saver.domain.ApplicationUser
import com.gavinflood.saver.domain.Credential
import com.gavinflood.saver.domain.Transaction
import com.gavinflood.saver.repository.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.util.*

/**
 * Service to load sample data.
 */
@Service
class SampleDataLoaderService(private val credentialRepository: CredentialRepository,
                              private val passwordEncoder: PasswordEncoder,
                              private val applicationUserRepository: ApplicationUserRepository,
                              private val roleRepository: RoleRepository,
                              private val accountRepository: AccountRepository,
                              private val accountTypeRepository: AccountTypeRepository,
                              private val transactionRepository: TransactionRepository,
                              private val transactionCategoryRepository: TransactionCategoryRepository) {

    @Value("classpath:import/sample-data.json")
    private lateinit var sampleDataResource: Resource

    protected val logger: Logger = LoggerFactory.getLogger(SampleDataLoaderService::class.java)
    protected val jsonMapper: ObjectMapper = ObjectMapper().registerModule(KotlinModule())

    /**
     * Load sample data.
     */
    fun loadSampleData() {
        val sampleData = loadFromImportFile()
        val userMap = mutableMapOf<Int, ApplicationUser>()
        loadUsers(sampleData, userMap)
        loadAccounts(sampleData, userMap)
    }

    /**
     * Process the import file.
     */
    private fun loadFromImportFile(): SampleDataImport {
        val logPrefix = "#loadFromImportFile ::"
        logger.info("$logPrefix Loading sample data")

        return jsonMapper.readValue(sampleDataResource.file, SampleDataImport::class.java)
    }

    private fun loadUsers(sampleData: SampleDataImport, userMap: MutableMap<Int, ApplicationUser>) {
        sampleData.users.forEach { userImport -> userMap[userImport.key] = createUser(userImport) }
    }

    private fun createCredential(credentialImport: CredentialImport): Credential {
        val credential = Credential(credentialImport.username, passwordEncoder.encode(credentialImport.password))
        return credentialRepository.save(credential)
    }

    private fun createUser(userImport: UserImport): ApplicationUser {
        val credential = createCredential(userImport.credential)
        val applicationUser = ApplicationUser(userImport.firstName, userImport.lastName, credential)
        applicationUser.roles = userImport.roles.map { role -> roleRepository.findByName(role).get() }.toMutableSet()
        return applicationUserRepository.save(applicationUser)
    }

    private fun loadAccounts(sampleData: SampleDataImport, userMap: Map<Int, ApplicationUser>) {
        sampleData.accounts.forEach { accountImport -> createAccount(accountImport, userMap) }
    }

    private fun createAccount(accountImport: AccountImport, userMap: Map<Int, ApplicationUser>) {
        val users = userMap.filterKeys { key -> accountImport.users.contains(key) }.values.toMutableSet()
        val accountType = accountTypeRepository.findByCode(accountImport.accountType).get()
        val account = accountRepository.save(Account(accountImport.name, accountType, users))
        users.forEach { user ->
            user.accounts.add(account)
            applicationUserRepository.save(user)
        }
        accountImport.transactions.forEach { transactionImport -> createTransaction(transactionImport, account) }
    }

    private fun createTransaction(transactionImport: TransactionImport, account: Account) {
        val category = transactionCategoryRepository.findByCode(transactionImport.category).get()
        val transaction = Transaction(transactionImport.description, transactionImport.amount, account,
                transactionImport.date, category)
        transactionRepository.save(transaction)
    }

}

data class SampleDataImport(val users: MutableList<UserImport> = mutableListOf(),
                            val accounts: MutableList<AccountImport> = mutableListOf())

data class UserImport(val key: Int, val firstName: String, val lastName: String, val credential: CredentialImport,
                      val roles: MutableList<String> = mutableListOf())

data class CredentialImport(val username: String, val password: String)

data class AccountImport(val key: Int, val name: String, val accountType: String,
                         val users: MutableList<Int> = mutableListOf(),
                         val transactions: MutableList<TransactionImport> = mutableListOf())

data class TransactionImport(val key: Int, val description: String, val amount: BigDecimal, val date: Date,
                             val category: String)