package com.gavinflood.saver.service

import com.gavinflood.saver.config.constants.TypeConstants
import com.gavinflood.saver.domain.Type
import com.gavinflood.saver.repository.TypeRepository
import org.springframework.stereotype.Service
import java.util.*
import kotlin.reflect.KClass

/**
 * Business logic for a [Type].
 *
 * @param repository The repository to interact with the database layer
 */
@Service
class TypeService(repository: TypeRepository) : BaseService<Type, TypeRepository>(repository) {

    /**
     * Find one type based on the group and code.
     *
     * @param clazz Identifies the group
     * @param code Identifies the type for the group
     * @return The matching type, if found
     */
    fun findOne(clazz: KClass<*>, code: String): Optional<Type> {
        val group = TypeConstants.getGroup(clazz)
        return repository.findByGroupAndCode(group, code)
    }

    /**
     * Find all types based on their group.
     *
     * @param group Identifies the group
     * @return The matching types
     */
    fun findAll(group: String): List<Type> {
        return repository.findAllByGroup(group)
    }

}