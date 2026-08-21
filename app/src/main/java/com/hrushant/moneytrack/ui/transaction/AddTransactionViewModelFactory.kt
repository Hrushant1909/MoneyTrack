package com.hrushant.moneytrack.ui.transaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hrushant.moneytrack.data.local.session.SessionManager
import com.hrushant.moneytrack.data.repository.CategoryRepository
import com.hrushant.moneytrack.data.repository.TransactionRepository

class AddTransactionViewModelFactory(
    private val categoryRepository: CategoryRepository,
    private val transactionRepository: TransactionRepository,
    private val sessionManager: SessionManager
) : ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(AddTransactionViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return AddTransactionViewModel(
                categoryRepository,
                transactionRepository,
                sessionManager
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }

}