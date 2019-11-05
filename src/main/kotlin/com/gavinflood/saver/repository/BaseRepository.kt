package com.gavinflood.saver.repository

import com.gavinflood.saver.domain.IdentifiableEntity
import org.springframework.data.repository.PagingAndSortingRepository

/**
 * Base repository interface.
 */
interface BaseRepository<T : IdentifiableEntity> : PagingAndSortingRepository<T, Long>