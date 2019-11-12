package com.gavinflood.saver.config.constants

import com.gavinflood.saver.domain.AccountType
import com.gavinflood.saver.domain.exception.TypeGroupNotFoundException
import kotlin.reflect.KClass

/**
 * Type constants.
 */
class TypeConstants {

    companion object {

        // Maps a class to a type group value
        private val groups = mutableMapOf<KClass<*>, String>(
                AccountType::class to "account"
        )

        // Account type codes
        const val ACCOUNT_CURRENT = "current"
        const val ACCOUNT_SAVINGS = "savings"
        const val ACCOUNT_CREDIT = "credit"

        /**
         * Get the related type group value based for a given [com.gavinflood.saver.domain.Type] subclass.
         *
         * @param clazz The class
         * @return The group value associated with the class
         */
        fun getGroup(clazz: KClass<*>): String {
            return groups[clazz] ?: throw TypeGroupNotFoundException(clazz)
        }

    }

}