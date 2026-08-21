package com.hrushant.moneytrack.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hrushant.moneytrack.data.entity.Transaction
import com.hrushant.moneytrack.data.local.session.SessionManager
import com.hrushant.moneytrack.data.repository.CategoryRepository
import com.hrushant.moneytrack.data.repository.TransactionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class DashboardViewModel(
    private val categoryRepository: CategoryRepository,
    private val transactionRepository: TransactionRepository,
    private val sessionManager: SessionManager
) : ViewModel() {


    private val _transactions = MutableStateFlow<List<Transaction>>(emptyList())
    val transactions: StateFlow<List<Transaction>> = _transactions.asStateFlow()

    fun initializeCategories(){
        viewModelScope.launch {
            categoryRepository.initializeCategories()
        }
    }


    fun loadTransactions(){
        viewModelScope.launch {
            val userId = sessionManager.loggedInUserId.first()

            if(userId == null)
                return@launch

            transactionRepository.getTransactionByUser(userId)
                .collect {transaction ->
                    _transactions.value = transaction
                }
        }
    }

}