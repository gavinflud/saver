package com.gavinflood.saver.repository

import com.gavinflood.saver.domain.Role
import org.springframework.stereotype.Repository
import java.util.*

/**
 * Interface for operations on the Role repository.
 */
@Repository
interface RoleRepository : BaseRepository<Role> {

    /**
     * Find a Role by its name.
     *
     * @param name The role name which should be unique
     * @return The matching role, if found
     */
    fun findByName(name: String): Optional<Role>

}