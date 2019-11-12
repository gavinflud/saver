package com.gavinflood.saver.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import com.gavinflood.saver.config.constants.SecurityConstants
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import javax.persistence.*
import javax.validation.constraints.Size


/**
 * A user of the application. A user can be anything from a registered user who owns multiple accounts, to an
 * administrator.
 *
 * @param firstName The first name
 * @param lastName The last name
 * @param credential The credential that authenticates the user
 */
@Entity(name = "gf_application_user")
class ApplicationUser(

        @field:Size(min = 2)
        @Column(name = "gf_first_name")
        var firstName: String,

        @field:Size(min = 2)
        @Column(name = "gf_last_name")
        var lastName: String,

        /**
         * The credential the user can authenticate with.
         */
        @OneToOne(cascade = [CascadeType.ALL])
        @JoinColumn(name = "gf_credential_id", referencedColumnName = "gf_id")
        var credential: Credential


) : IdentifiableEntity() {

    /**
     * The roles assigned to the user.
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "gf_application_user_roles",
            joinColumns = [JoinColumn(name = "gf_application_user_id", referencedColumnName = "gf_id")],
            inverseJoinColumns = [JoinColumn(name = "gf_role_id", referencedColumnName = "gf_id")])
    var roles = mutableSetOf<Role>()

    /**
     * The accounts the user has access to.
     */
    @ManyToMany
    @JoinTable(name = "gf_application_user_accounts",
            joinColumns = [JoinColumn(name = "gf_application_user_id", referencedColumnName = "gf_id")],
            inverseJoinColumns = [JoinColumn(name = "gf_account_id", referencedColumnName = "gf_id")])
    var accounts = mutableSetOf<Account>()

    /**
     * @param _firstName The first name
     * @param _lastName The last name
     * @param _credential The credential that authenticates the user
     * @param _roles The roles assigned to the user
     */
    constructor(_firstName: String, _lastName: String, _credential: Credential, _roles: MutableSet<Role>)
            : this(_firstName, _lastName, _credential) {
        roles = _roles
    }

    /**
     * Return the user's full name.
     */
    @JsonIgnore
    fun getFullName(): String {
        return "$firstName $lastName"
    }

    /**
     * @return The set of granted authorities the user holds. These are based off the permissions that
     * exist under each role assigned to the user.
     */
    @JsonIgnore
    fun getAuthorities(): Set<GrantedAuthority> {
        return roles
                .flatMap { role -> role.permissions }
                .map { permission -> SimpleGrantedAuthority(SecurityConstants.getAuthorityName(permission.name)) }
                .toSet()
    }

    /**
     * @return True if the user has the admin permission. False otherwise.
     */
    fun isAdmin(): Boolean {
        return getAuthorities()
                .map { grantedAuthority -> grantedAuthority.authority }
                .contains(SecurityConstants.PERMISSION_ADMIN)
    }
}