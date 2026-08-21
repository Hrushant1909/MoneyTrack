package com.hrushant.moneytrack.ui.transaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hrushant.moneytrack.data.entity.Category
import com.hrushant.moneytrack.data.entity.Transaction
import com.hrushant.moneytrack.data.entity.TransactionType
import com.hrushant.moneytrack.data.local.session.SessionManager
import com.hrushant.moneytrack.data.repository.CategoryRepository
import com.hrushant.moneytrack.data.repository.TransactionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class AddTransactionViewModel(
    private val categoryRepository: CategoryRepository,
    private val transactionRepository: TransactionRepository,
    private val sessionManager: SessionManager
) : ViewModel(){

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories: StateFlow<List<Category>> =
        _categories.asStateFlow()
    fun loadCategories(type: TransactionType)
    {
        viewModelScope.launch {
            categoryRepository
                .getCategoriesByType(type)
                .collect { categories ->
                    _categories.value = categories
                }
        }
    }


    fun saveTransaction(
        amount: Double,
        type: TransactionType,
        categoryId: Int,
        description: String
    ){
        viewModelScope.launch {
            val userId = sessionManager.loggedInUserId.first()
            if(userId == null){
                return@launch
            }
            val transaction = Transaction(
                userId = userId,
                amount = amount,
                type = type,
                categoryId = categoryId,
                description = description,
                date = System.currentTimeMillis(),
                createdAt = System.currentTimeMillis()
            )
            transactionRepository.insertTransaction(transaction)
        }
    }

}