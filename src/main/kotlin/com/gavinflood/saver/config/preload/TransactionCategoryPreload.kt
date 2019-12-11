package com.gavinflood.saver.config.preload

import com.gavinflood.saver.domain.TransactionCategory
import com.gavinflood.saver.repository.TransactionCategoryRepository
import org.springframework.core.io.ResourceLoader
import org.springframework.stereotype.Component
import java.io.File

/**
 * Preloads [TransactionCategory] entries into the database on server startup.
 */
@Component
class TransactionCategoryPreload(resourceLoader: ResourceLoader,
                                 private val transactionCategoryRepository: TransactionCategoryRepository)
    : JsonPreload() {

    init {
        importResource = resourceLoader.getResource("classpath:import/transaction-categories.json")
    }

    /**
     * Processes the JSON file.
     */
    override fun processJson(importFile: File) {
        val dataImport = jsonMapper.readValue(importFile, TransactionCategoryDataImport::class.java)
        dataImport.data.forEach { importData -> preloadTransactionCategory(importData) }
    }

    /**
     * Preload an individual TransactionCategory entry.
     *
     * @param importData The entry object
     */
    private fun preloadTransactionCategory(importData: TransactionCategoryImport) {
        val logPrefix = "#preloadAccountType ::"
        logger.debug("$logPrefix Importing '${importData.name}'")

        val existingTransactionCategory = transactionCategoryRepository.findByCode(importData.code)
        val transactionCategory = if (existingTransactionCategory.isPresent) {
            val type = existingTransactionCategory.get()
            type.name = importData.name
            type.description = importData.name
            type
        } else TransactionCategory(importData.code, importData.name, importData.description)
        transactionCategoryRepository.save(transactionCategory)
    }

}

private data class TransactionCategoryDataImport(val data: List<TransactionCategoryImport> = emptyList())

data class TransactionCategoryImport(val code: String, val name: String, val description: String)