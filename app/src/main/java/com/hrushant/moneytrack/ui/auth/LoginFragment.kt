package com.hrushant.moneytrack.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.hrushant.moneytrack.R
import com.hrushant.moneytrack.data.local.database.DatabaseProvider
import com.hrushant.moneytrack.data.repository.UserRepository
import com.hrushant.moneytrack.databinding.FragmentLoginBinding
import kotlinx.coroutines.launch
import kotlinx.serialization.EncodeDefault

class LoginFragment : Fragment(R.layout.fragment_login) {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LoginViewModel by viewModels {
        val database = DatabaseProvider.getDatabase(requireContext())
        val repository = UserRepository(database.userDao())
        LoginViewModelFactory(repository)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentLoginBinding.bind((view))
        binding.tvRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }


        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString()

            if(email.isEmpty() || password.isEmpty()){
                return@setOnClickListener
            }

            viewModel.login(
                email = email,
                password = password
            )
        }
        observeUiState()
    }

    private fun observeUiState() {

        viewLifecycleOwner.lifecycleScope.launch {

            viewLifecycleOwner.repeatOnLifecycle(
                Lifecycle.State.STARTED
            ) {

                viewModel.uiState.collect { state ->

                    when (state) {

                        LoginUiState.Idle -> {
                            // Nothing to do
                        }

                        LoginUiState.Loading -> {
                            binding.btnLogin.isEnabled = false
                            binding.btnLogin.text = "LOGGING IN..."
                        }

                        LoginUiState.Success -> {
                            binding.btnLogin.isEnabled = true
                            binding.btnLogin.text = "LOGIN"

                            // Dashboard navigation will be added later
                        }

                        LoginUiState.UserNotFound -> {
                            binding.btnLogin.isEnabled = true
                            binding.btnLogin.text = "LOGIN"

                            binding.emailLayout.error =
                                "No account found with this email"
                        }

                        LoginUiState.InvalidCredentials -> {
                            binding.btnLogin.isEnabled = true
                            binding.btnLogin.text = "LOGIN"

                            binding.passwordLayout.error =
                                "Incorrect password"
                        }

                        is LoginUiState.Error -> {
                            binding.btnLogin.isEnabled = true
                            binding.btnLogin.text = "LOGIN"

                            binding.emailLayout.error =
                                state.message
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}