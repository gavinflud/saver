package com.gavinflood.saver.helper.builder

import com.gavinflood.saver.config.security.constant.Constants
import com.gavinflood.saver.domain.Permission

class PermissionBuilder(private var name: String = Constants.PERMISSION_DEFAULT) : BaseBuilder<Permission>() {

    fun withName(_name: String): PermissionBuilder {
        name = _name
        return this
    }

    override fun build(): Permission {
        return Permission(name)
    }

}