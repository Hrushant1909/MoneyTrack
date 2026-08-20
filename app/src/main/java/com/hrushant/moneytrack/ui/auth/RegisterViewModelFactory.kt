package com.hrushant.moneytrack.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hrushant.moneytrack.data.repository.UserRepository

class RegisterViewModelFactory(
    private val userRepository: UserRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if(modelClass.isAssignableFrom(RegisterViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return RegisterViewModel(userRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}