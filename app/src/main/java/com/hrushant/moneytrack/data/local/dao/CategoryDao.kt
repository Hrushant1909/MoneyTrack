package com.hrushant.moneytrack.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.hrushant.moneytrack.data.entity.Category
import com.hrushant.moneytrack.data.entity.TransactionType
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {

    @Insert
    suspend fun insertCategories(categories: List<Category>)

    @Query("SELECT * FROM categories WHERE type = :type")
    fun getCategoriesByType(type: TransactionType): Flow<List<Category>>

    @Query("SELECT * FROM categories")
    fun getAllCategories(): Flow<List<Category>>

}