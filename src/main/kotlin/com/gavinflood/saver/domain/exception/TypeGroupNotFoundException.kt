package com.gavinflood.saver.domain.exception

import kotlin.reflect.KClass

/**
 * Thrown when you try to find a type group based on a class but none exists.
 */
class TypeGroupNotFoundException(clazz: KClass<*>)
    : RuntimeException("No entry for type '${clazz.simpleName}' found in type constants")