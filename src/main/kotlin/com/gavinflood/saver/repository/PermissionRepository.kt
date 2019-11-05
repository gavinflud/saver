package com.gavinflood.saver.repository

import com.gavinflood.saver.domain.Permission
import org.springframework.stereotype.Repository
import java.util.*

/**
 * Interface for operations on the Permission repository.
 */
@Repository
interface PermissionRepository : BaseRepository<Permission> {

    /**
     * Find a Permission by its name.
     *
     * @param name The permission name which should be unique
     * @return The matching permission, if found
     */
    fun findByName(name: String): Optional<Permission>

}