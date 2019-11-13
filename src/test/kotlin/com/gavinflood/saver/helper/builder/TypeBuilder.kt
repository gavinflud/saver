package com.gavinflood.saver.helper.builder

import com.gavinflood.saver.domain.Type

abstract class TypeBuilder(protected var group: String, protected var code: String, protected var name: String,
                           protected var description: String) : BaseBuilder<Type>() {

    fun withGroup(group: String): TypeBuilder {
        this.group = group
        return this
    }

    fun withCode(code: String): TypeBuilder {
        this.code = code
        return this
    }

    fun withName(name: String): TypeBuilder {
        this.name = name
        return this
    }

    fun withDescription(description: String): TypeBuilder {
        this.description = description
        return this
    }

}