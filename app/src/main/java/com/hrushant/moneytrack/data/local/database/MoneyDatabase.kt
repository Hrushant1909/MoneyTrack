package com.hrushant.moneytrack.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.hrushant.moneytrack.data.entity.Category
import com.hrushant.moneytrack.data.entity.Transaction
import com.hrushant.moneytrack.data.entity.TransactionType
import com.hrushant.moneytrack.data.entity.User
import com.hrushant.moneytrack.data.local.dao.CategoryDao
import com.hrushant.moneytrack.data.local.dao.TransactionDao
import com.hrushant.moneytrack.data.local.dao.UserDao


@Database(
    entities = [
        User::class,
        Category::class,
        Transaction::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class MoneyDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun categoryDao(): CategoryDao

    abstract fun transactionDao(): TransactionDao



}