package com.gavinflood.saver.controller

import com.gavinflood.saver.domain.IdentifiableEntity
import com.gavinflood.saver.service.BaseService

/**
 * Base implementation of a rest controller.
 *
 * @param service The service used by the entity this controller applies to
 */
abstract class BaseController<T : IdentifiableEntity, S : BaseService<T>>(protected open val service: S)