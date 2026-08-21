package com.hrushant.moneytrack.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hrushant.moneytrack.data.repository.CategoryRepository

class DashboardViewModelFactory(
    private val categoryRepository: CategoryRepository
) : ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(DashboardViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return DashboardViewModel(categoryRepository) as T

        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }

}