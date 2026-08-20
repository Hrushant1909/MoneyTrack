package com.hrushant.moneytrack.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hrushant.moneytrack.data.entity.User
import com.hrushant.moneytrack.data.repository.UserRepository
import com.hrushant.moneytrack.utils.PasswordHasher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val userRepository: UserRepository
) : ViewModel(){

    private val _uiState = MutableStateFlow<RegisterUiState>(RegisterUiState.Idle)
    val uiState : StateFlow<RegisterUiState> = _uiState.asStateFlow()


    fun registerUser(name: String,
                     email: String,
                     password: String){
        viewModelScope.launch {
            _uiState.value = RegisterUiState.Loading
            try {

                val passwordHash = PasswordHasher.hash(password)

                val user = User(
                    name = name,
                    email = email,
                    passwordHash = passwordHash
                )


                val success = userRepository.registerUser(user)
                if(success){
                    _uiState.value = RegisterUiState.Success
                }
                else{
                    _uiState.value = RegisterUiState.EmailAlreadyExists
                }
            } catch (e: Exception){
                _uiState.value = RegisterUiState.Error(e.message ?: "Registration Failed")
            }
        }
    }

}