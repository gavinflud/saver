package com.gavinflood.saver.domain

import javax.persistence.*

/**
 * Superclass that all entities that require a unique ID should inherit from.
 */
@MappedSuperclass
abstract class IdentifiableEntity {

    /**
     * The unique ID for the entity subtype.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "gf_id")
    val id: Long = 0

}