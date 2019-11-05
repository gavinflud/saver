package com.gavinflood.saver.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.ManyToMany

/**
 * A permission gives a user access to do a specific operation. Permissions are grouped under roles and map to granted
 * authorities in Spring. Users are assigned roles rather than specific permissions.
 */
@Entity(name = "gf_permission")
class Permission(@Column(name = "gf_name") var name: String) : IdentifiableEntity() {

    /**
     * The roles the permission is assigned to.
     */
    @ManyToMany(mappedBy = "permissions")
    @JsonIgnore
    var roles: MutableSet<Role> = mutableSetOf()

}