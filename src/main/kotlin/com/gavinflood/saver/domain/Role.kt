package com.gavinflood.saver.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

/**
 * A role is assigned to a user and contains a group of permissions. An example would be an administrator role,
 * which should contain more permissions than a general user role would.
 */
@Entity(name = "gf_role")
class Role(@Column(name = "gf_name") var name: String) : IdentifiableEntity() {

    /**
     * The users that have this role.
     */
    @ManyToMany(mappedBy = "roles")
    @JsonIgnore
    var users: MutableSet<ApplicationUser> = mutableSetOf()

    /**
     * The permissions that this role holds.
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "gf_role_permissions",
            joinColumns = [JoinColumn(name = "gf_role_id", referencedColumnName = "gf_id")],
            inverseJoinColumns = [JoinColumn(name = "gf_permission_id", referencedColumnName = "gf_id")])
    var permissions: MutableSet<Permission> = mutableSetOf()

    constructor(_name: String, _permissions: MutableSet<Permission>) : this(_name) {
        permissions = _permissions
    }

}