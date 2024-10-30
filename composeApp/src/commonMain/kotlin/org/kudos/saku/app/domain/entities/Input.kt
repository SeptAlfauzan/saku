package org.kudos.saku.app.domain.entities

data class Input<T>(
    val value: T,
    val error: String? = null,
)