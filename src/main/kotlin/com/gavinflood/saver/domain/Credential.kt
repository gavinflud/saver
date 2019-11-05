package com.gavinflood.saver.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import com.gavinflood.saver.domain.validation.annotation.ValidPassword
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.OneToOne
import javax.validation.constraints.Size

/**
 * A username and password combination to allow a user to authenticate.
 */
@Entity(name = "gf_credential")
class Credential(

        @Size(min = 2)
        @Column(name = "gf_username")
        var username: String,

        @ValidPassword
        @Column(name = "gf_password")
        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        var password: String

) : IdentifiableEntity() {

    /**
     * The user the credential belongs to.
     *
     * This can be null initially as the credential is created before the user is persisted.
     */
    @OneToOne(mappedBy = "credential")
    @JsonIgnore
    var applicationUser: ApplicationUser? = null

}