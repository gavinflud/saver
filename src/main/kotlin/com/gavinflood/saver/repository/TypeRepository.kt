package com.gavinflood.saver.repository

import com.gavinflood.saver.domain.Type
import org.springframework.stereotype.Repository
import java.util.*

/**
 * Interface for operations on the Type repository.
 */
@Repository
interface TypeRepository : BaseRepository<Type> {

    /**
     * Find a type based on the group and code.
     *
     * @param group Identifies the group
     * @param code Identifies the type for the group
     * @return The matching type, if found
     */
    fun findByGroupAndCode(group: String, code: String): Optional<Type>

    /**
     * Find all types under a particular group.
     *
     * @param group Identifies the group
     * @return The matching types
     */
    fun findAllByGroup(group: String): List<Type>

}