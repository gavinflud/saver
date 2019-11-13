package com.gavinflood.saver.service

import com.gavinflood.saver.domain.IdentifiableEntity
import com.gavinflood.saver.repository.BaseRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import javax.persistence.NoResultException

/**
 * Base implementation of a service. Handles the business logic to be performed on entities.
 *
 * @param repository The repository used by the entity this service applies to
 */
abstract class BaseService<T : IdentifiableEntity, S : BaseRepository<T>>(protected open val repository: S) {

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
     * @return The found resource
     */
    open fun findOne(id: Long): T {
        val result = repository.findById(id)
        if (result.isPresent) {
            return result.get()
        }

        throw NoResultException("No entity with ID '$id' found")
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
        val resource = findOne(id)
        repository.delete(resource)
        return resource
    }

}