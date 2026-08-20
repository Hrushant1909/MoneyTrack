package com.hrushant.moneytrack.ui.auth

sealed class RegisterUiState {

    object Idle : RegisterUiState()
    object Loading: RegisterUiState()
    object Success: RegisterUiState()
    object EmailAlreadyExists: RegisterUiState()

    data class Error(
        val message: String
    ): RegisterUiState()

}