package com.gavinflood.saver.service

import com.gavinflood.saver.domain.IdentifiableEntity
import com.gavinflood.saver.repository.BaseRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.util.*
import javax.persistence.NoResultException

/**
 * Base implementation of a service. Handles the business logic to be performed on entities.
 *
 * @param repository The repository used by the entity this service applies to
 */
abstract class BaseService<T : IdentifiableEntity>(protected open val repository: BaseRepository<T>) {

    /**
     * Create and save a new resource.
     *
     * @param resource The new resource
     * @return The persisted resource
     */
    open fun create(resource: T): T {
        return repository.save(resource)
    }

    /**
     * Find one resource using its unique ID.
     *
     * @param id The ID identifying the resource
     * @return An Optional wrapper of the result
     */
    open fun findOne(id: Long): Optional<T> {
        return repository.findById(id)
    }

    /**
     * Find all resources.
     *
     * @param pageable Defines the paging structure for the result
     * @return A page of results
     */
    open fun findAll(pageable: Pageable): Page<T> {
        return repository.findAll(pageable)
    }

    /**
     * Update an existing resource.
     *
     * @param resource The resource to update
     * @return The persisted resource with the changes intact
     */
    open fun update(resource: T): T {
        return repository.save(resource)
    }

    /**
     * Delete a resource using its unique ID.
     *
     * @param id The ID identifying the resource
     * @return The resource as it was before it was deleted
     */
    open fun delete(id: Long): T {
        val entityResult = findOne(id)
        if (entityResult.isPresent) {
            val entity = entityResult.get()
            repository.delete(entity)
            return entity
        }

        throw NoResultException("No entity with ID '$id' found")
    }

}