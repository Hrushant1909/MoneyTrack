package com.hrushant.moneytrack.ui.auth

sealed class LoginUiState {

    object Idle : LoginUiState()

    object Loading : LoginUiState()

    object Success : LoginUiState()

    object InvalidCredentials : LoginUiState()

    object UserNotFound : LoginUiState()

    data class Error(
        val message: String
    ) : LoginUiState()
}