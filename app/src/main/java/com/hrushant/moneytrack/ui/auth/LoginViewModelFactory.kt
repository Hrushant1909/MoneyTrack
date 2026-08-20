package com.hrushant.moneytrack.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hrushant.moneytrack.data.repository.UserRepository

class LoginViewModelFactory(
    private val userRepository: UserRepository
) : ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if(modelClass.isAssignableFrom(LoginViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(userRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }

}