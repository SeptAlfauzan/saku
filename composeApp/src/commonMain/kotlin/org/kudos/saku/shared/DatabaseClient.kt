package org.kudos.saku.shared

import org.kudos.saku.app.data.source.local.room.Database

expect class DatabaseClient{
    fun getDatabaseInstance() : Database
}