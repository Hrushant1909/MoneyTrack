package com.hrushant.moneytrack.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hrushant.moneytrack.data.entity.User
import com.hrushant.moneytrack.data.local.dao.UserDao


@Database(
    entities = [User::class],
    version = 1,
    exportSchema = false
)
abstract class MoneyDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

}