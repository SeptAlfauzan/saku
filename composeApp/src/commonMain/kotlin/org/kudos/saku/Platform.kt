package org.kudos.saku

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform