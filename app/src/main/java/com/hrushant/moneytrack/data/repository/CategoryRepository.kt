package com.hrushant.moneytrack.data.repository

import com.hrushant.moneytrack.data.entity.Category
import com.hrushant.moneytrack.data.entity.TransactionType
import com.hrushant.moneytrack.data.local.dao.CategoryDao
import com.hrushant.moneytrack.data.model.DefaultCategories
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class CategoryRepository(
    private val categoryDao: CategoryDao
) {

    fun getAllCategories(): Flow<List<Category>> {
        return categoryDao.getAllCategories()
    }

    fun getCategoriesByType(
        type: TransactionType
    ): Flow<List<Category>> {
        return categoryDao.getCategoriesByType(type)
    }

    suspend fun initializeCategories() {

        val existingCategories =
            categoryDao.getAllCategories().first()

        if (existingCategories.isEmpty()) {
            categoryDao.insertCategories(
                DefaultCategories.categories
            )
        }
    }
}