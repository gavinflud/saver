package com.gavinflood.saver.controller

import com.gavinflood.saver.domain.IdentifiableEntity
import com.gavinflood.saver.domain.response.sendOkResponse
import com.gavinflood.saver.repository.BaseRepository
import com.gavinflood.saver.service.BaseService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity

/**
 * Base implementation of a rest controller.
 *
 * @param service The service used by the entity this controller applies to
 */
abstract class BaseController<T : IdentifiableEntity, R : BaseRepository<T>, S : BaseService<T, R>>
(protected open val service: S) {

    /**
     * Base implementation to create a new user.
     *
     * @param resource The resource to create
     * @return The created resource
     */
    open fun create(resource: T): ResponseEntity<T> {
        return sendOkResponse(service.create(resource))
    }

    /**
     * Base implementation to find a single instance of a resource.
     *
     * @param id Identifies the resource
     * @return The found resource
     */
    open fun findOne(id: Long): ResponseEntity<T> {
        return sendOkResponse(service.findOne(id))
    }

    /**
     * Base implementation to find all instances of a resource.
     *
     * @param pageable Defines the paging structure for the results
     * @return The found resources in [Page] format
     */
    open fun findAll(pageable: Pageable): ResponseEntity<Page<T>> {
        return sendOkResponse(service.findAll(pageable))
    }

}