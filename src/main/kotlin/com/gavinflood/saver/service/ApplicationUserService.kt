package com.gavinflood.saver.service

import com.gavinflood.saver.domain.ApplicationUser
import com.gavinflood.saver.repository.ApplicationUserRepository
import org.springframework.stereotype.Service

/**
 * Business logic for an [ApplicationUser].
 *
 * @param repository The repository to interact with the database layer
 */
@Service
class ApplicationUserService(repository: ApplicationUserRepository) : BaseService<ApplicationUser>(repository) {


}