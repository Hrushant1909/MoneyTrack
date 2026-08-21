package com.hrushant.moneytrack.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hrushant.moneytrack.data.repository.CategoryRepository
import kotlinx.coroutines.launch

class DashboardViewModel(
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    fun initializeCategories(){
        viewModelScope.launch {
            categoryRepository.initializeCategories()
        }
    }

}