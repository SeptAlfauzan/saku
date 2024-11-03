package org.kudos.saku.app.data.source.local.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import org.kudos.saku.app.data.source.local.room.entities.CashFlowEntity

@Dao
interface CashFlowDao {
    @Insert
    suspend fun insert(item: CashFlowEntity)

    @Query("SELECT count(*) FROM CashFlowEntity")
    suspend fun count(): Int

    @Query("SELECT * FROM CashFlowEntity")
    fun getAllAsFlow(): Flow<List<CashFlowEntity>>

    @Query("SELECT * FROM CashFlowEntity where created = :date")
    fun getByDateAsFlow(date: String): Flow<List<CashFlowEntity>>

    @Delete
    suspend fun delete(item: CashFlowEntity)

    @Upsert
    suspend fun upsert(item: CashFlowEntity)
}