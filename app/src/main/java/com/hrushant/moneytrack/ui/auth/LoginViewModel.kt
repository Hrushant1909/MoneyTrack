package com.hrushant.moneytrack.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hrushant.moneytrack.data.repository.UserRepository
import com.hrushant.moneytrack.utils.PasswordHasher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Idle)
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()


    fun login(
        email: String,
        password: String
    ){
        viewModelScope.launch {

            _uiState.value = LoginUiState.Loading

            try {
                val user = userRepository.getUserByEmail(email)
                if(user == null){
                    _uiState.value = LoginUiState.UserNotFound
                    return@launch
                }

                val passwordCorrect = PasswordHasher.verify(
                    password,
                    user.passwordHash
                )

                if(passwordCorrect){
                    _uiState.value = LoginUiState.Success
                } else {
                    _uiState.value = LoginUiState.InvalidCredentials
                }
            }catch (e: Exception){
                _uiState.value = LoginUiState.Error(
                    e.message ?: "Login Failed"
                )
            }
        }
    }
}