package com.hrushant.moneytrack.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class Transaction(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val userId: Int,

    val amount: Double,

    val type: TransactionType,

    val categoryId: Int,

    val description: String,

    val date: Long,

    val createdAt: Long
)