package com.gavinflood.saver.config.preload

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.gavinflood.saver.domain.Type
import com.gavinflood.saver.repository.TypeRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.stereotype.Component

/**
 * Imports all type entries into the database.
 *
 * @param typeRepository Type repository
 */
@Component
class TypePreload(val typeRepository: TypeRepository) {

    private val logger = LoggerFactory.getLogger(TypePreload::class.java)

    @Value(value = "classpath:import/types.json")
    private lateinit var importResource: Resource

    /**
     * Run the import.
     */
    fun run() {
        val logPrefix = "#run ::"
        logger.info("$logPrefix Importing types from JSON")

        val jsonMapper = ObjectMapper().registerModule(KotlinModule())
        val importFile = importResource.file
        val typeImport = jsonMapper.readValue(importFile, TypeImport::class.java)
        typeImport.groups.forEach { group -> preloadTypeGroup(group) }

        logger.info("$logPrefix Importing types completed")
    }

    /**
     * Import the types for a particular group.
     *
     * @param typeGroupImport Stores the group name and the types associated with it
     */
    fun preloadTypeGroup(typeGroupImport: TypeGroupImport) {
        typeGroupImport.types.forEach { type -> preloadType(typeGroupImport.name, type) }
    }

    /**
     * Import a type.
     *
     * @param group The type group
     * @param typeItem Stores the rest of the type data
     */
    fun preloadType(group: String, typeItem: TypeItemImport) {
        val logPrefix = "#preloadType ::"
        logger.debug("$logPrefix Importing $group-${typeItem.code}")

        // If a matching type exists, update it. Otherwise, create a new one.
        val typeResult = typeRepository.findByGroupAndCode(group, typeItem.code)
        val type = if (typeResult.isPresent) {
            val t = typeResult.get()
            t.name = typeItem.name
            t.description = typeItem.description
            t
        } else Type(group, typeItem.code, typeItem.name, typeItem.description)
        typeRepository.save(type)
    }

}

/**
 * Root type import object.
 *
 * @param groups The different type group data objects
 */
data class TypeImport(val groups: List<TypeGroupImport> = emptyList())

/**
 * Data object mapping the type group to a list of type data objects.
 *
 * @param name The group name
 * @param types The types that fall under this group
 */
data class TypeGroupImport(var name: String, val types: List<TypeItemImport> = emptyList())

/**
 * Data object for a type item. Does not contain the group name.
 *
 * @param code The type code
 * @param name The type name
 * @param description The type description
 */
data class TypeItemImport(val code: String, val name: String, val description: String)