package com.gavinflood.saver.helper.builder

interface Builder<T> {

    fun withDefaults(): Builder<T>

    fun build(): T

}