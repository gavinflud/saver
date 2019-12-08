package com.gavinflood.saver.config.preload

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.slf4j.LoggerFactory
import org.springframework.core.io.Resource
import java.io.File

/**
 * Imports JSON data into the database on server startup.
 */
abstract class JsonPreload {

    protected lateinit var importResource: Resource

    protected val logger = LoggerFactory.getLogger(JsonPreload::class.java)
    protected val jsonMapper = ObjectMapper().registerModule(KotlinModule())

    /**
     * Run the import.
     */
    fun run() {
        val logPrefix = "#run ::"
        logger.info("$logPrefix Importing types from JSON")

        val importFile = importResource.file
        processJson(importFile)

        logger.info("$logPrefix Importing types completed")
    }

    /**
     * Processes the JSON data and handles the importing of the data. To be implemented by any subclasses.
     */
    abstract fun processJson(importFile: File)

}