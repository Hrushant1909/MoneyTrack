package com.hrushant.moneytrack.data.local.database

import android.content.Context
import androidx.room.Room

object DatabaseProvider {

    @Volatile
    private var INSTANCE: MoneyDatabase? = null
    fun getDatabase(context: Context): MoneyDatabase{
        return INSTANCE ?: synchronized(this){
            val instance = Room.databaseBuilder(
                context.applicationContext,
                MoneyDatabase::class.java,
                "moneytrack_database"
            ).build()
            INSTANCE = instance
            instance
        }
    }

}