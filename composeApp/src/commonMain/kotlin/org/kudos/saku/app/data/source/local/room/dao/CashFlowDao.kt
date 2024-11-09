package org.kudos.saku.app.data.source.local.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import org.kudos.saku.app.data.source.local.room.entities.CashFlowEntity
import org.kudos.saku.app.domain.entities.MonthlyCashFlow

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

    @Query("""
        SELECT(
            COALESCE((SELECT SUM(amount) FROM CashFlowEntity where created = :date AND isCashIn = true), 0)
            -
            COALESCE((SELECT SUM(amount) FROM CashFlowEntity where created = :date AND isCashIn = false), 0)
        ) as difference
    """)
    fun getSelectedDateCashInCashOutDifference(date: String): Flow<Long>

    @Query("""
        SELECT(
            COALESCE((SELECT SUM(amount) FROM CashFlowEntity where created like :year|| '-'  || :month || '-%' AND isCashIn = true), 0)
            -
            COALESCE((SELECT SUM(amount) FROM CashFlowEntity where created like :year|| '-'  || :month || '-%' AND isCashIn = false), 0)
        ) as difference
    """)
    fun getSelectedMonthCashInCashOutDifference(month: String, year: Int): Flow<Long>

    @Query("""
        SELECT 
            strftime('%Y-%m', created) as month,
            SUM(CASE WHEN isCashIn = true THEN amount ELSE 0 END) as cashIn,
            SUM(CASE WHEN isCashIn = false THEN amount ELSE 0 END) as cashOut,
            SUM(CASE WHEN isCashIn = true THEN amount ELSE -amount END) as difference
        FROM CashFlowEntity
        GROUP BY strftime('%Y-%m', created)
        ORDER BY month
    """)
    fun getMonthlyCashInCashOutDifference(): Flow<List<MonthlyCashFlow>>

    @Query("""
        SELECT * FROM CashFlowEntity
        WHERE created = :date AND isCashIn = true
    """)
    fun getCashInByDate(date: String): Flow<List<CashFlowEntity>>

    @Query("""
        SELECT * FROM CashFlowEntity
        WHERE created = :date AND isCashIn = false
    """)
    fun getCashOutByDate(date: String): Flow<List<CashFlowEntity>>
}