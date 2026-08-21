package com.hrushant.moneytrack.data.repository

import com.hrushant.moneytrack.data.entity.Transaction
import com.hrushant.moneytrack.data.entity.TransactionType
import com.hrushant.moneytrack.data.local.dao.TransactionDao
import kotlinx.coroutines.flow.Flow

class TransactionRepository(
    private val transactionDao: TransactionDao
) {

    suspend fun insertTransaction(transaction: Transaction){
        transactionDao.insertTransaction(transaction)
    }

    fun getTransactionByUser(
        userId: Int
    ): Flow<List<Transaction>>{
        return transactionDao.getTransactionsByUser(userId)
    }

    fun getTransactionByUserAndType(
        userId: Int,
        type: TransactionType
    )
 : Flow<List<Transaction>>{
        return transactionDao.getTransactionsByUserAndType(userId, type)
    }

    suspend fun deleteTransaction(transaction: Transaction) {
        transactionDao.deleteTransaction(transaction)
    }

    suspend fun deleteAllTransactionsForUser(userId: Int) {
        transactionDao.deleteAllTransactionsForUser(userId)
    }
}