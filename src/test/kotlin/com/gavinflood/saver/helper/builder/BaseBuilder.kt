package com.gavinflood.saver.helper.builder

abstract class BaseBuilder<T> : Builder<T> {

    override fun withDefaults(): Builder<T> {
        return this
    }

}