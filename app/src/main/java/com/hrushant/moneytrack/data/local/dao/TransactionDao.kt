package com.hrushant.moneytrack.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.hrushant.moneytrack.data.entity.Transaction
import com.hrushant.moneytrack.data.entity.TransactionType
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {

    @Insert
    suspend fun insertTransaction(transaction: Transaction)

    @Query("""
        SELECT * FROM transactions
        WHERE userId = :userId
        ORDER BY date DESC
    """)
    fun getTransactionsByUser(userId: Int): Flow<List<Transaction>>


    @Query("""
        SELECT * FROM transactions
        WHERE userId = :userId
        AND type = :type
        ORDER BY date DESC
    """)
    fun getTransactionsByUserAndType(
        userId: Int,
        type: TransactionType
    ): Flow<List<Transaction>>

    @Delete
    suspend fun deleteTransaction(transaction: Transaction)

    @Query("""
        DELETE FROM transactions
        WHERE userId = :userId
    """)
    suspend fun deleteAllTransactionsForUser(userId: Int)
}