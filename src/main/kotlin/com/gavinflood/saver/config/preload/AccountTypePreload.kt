package com.gavinflood.saver.config.preload

import com.gavinflood.saver.domain.AccountType
import com.gavinflood.saver.repository.AccountTypeRepository
import org.springframework.core.io.ResourceLoader
import org.springframework.stereotype.Component
import java.io.File

/**
 * Preloads [AccountType] entries into the database on server startup.
 */
@Component
class AccountTypePreload(resourceLoader: ResourceLoader,
                         private val accountTypeRepository: AccountTypeRepository) : JsonPreload() {

    init {
        importResource = resourceLoader.getResource("classpath:import/account-types.json")
    }

    /**
     * Process the JSON file.
     */
    override fun processJson(importFile: File) {
        val dataImport = jsonMapper.readValue(importFile, AccountTypeDataImport::class.java)
        dataImport.data.forEach { importData -> preloadAccountType(importData) }
    }

    /**
     * Preload an individual AccountType entry.
     *
     * @param importData The entry object
     */
    private fun preloadAccountType(importData: AccountTypeImport) {
        val logPrefix = "#preloadAccountType ::"
        logger.debug("$logPrefix Importing '${importData.name}'")

        val existingAccountType = accountTypeRepository.findByCode(importData.code)
        val accountType = if (existingAccountType.isPresent) {
            val type = existingAccountType.get()
            type.name = importData.name
            type.description = importData.name
            type
        } else AccountType(importData.code, importData.name, importData.description)
        accountTypeRepository.save(accountType)
    }

}

private data class AccountTypeDataImport(val data: List<AccountTypeImport> = emptyList())

data class AccountTypeImport(val code: String, val name: String, val description: String)