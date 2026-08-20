package com.hrushant.moneytrack.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.hrushant.moneytrack.R
import com.hrushant.moneytrack.data.entity.User
import com.hrushant.moneytrack.data.local.database.DatabaseProvider
import com.hrushant.moneytrack.data.local.database.MoneyDatabase
import com.hrushant.moneytrack.data.repository.UserRepository
import com.hrushant.moneytrack.databinding.FragmentRegisterBinding
import kotlinx.coroutines.launch

class RegisterFragment : Fragment(R.layout.fragment_register) {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private val viewModel : RegisterViewModel by viewModels {
        val database = DatabaseProvider.getDatabase(requireContext())
        val repository = UserRepository(database.userDao())
        RegisterViewModelFactory(repository)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentRegisterBinding.bind(view)

        binding.tvLogin.setOnClickListener {
            findNavController().navigate(
                R.id.action_registerFragment_to_loginFragment
            )
        }



        //registering user button and validating the user input
        binding.btnRegister.setOnClickListener {
            val name = binding.etName.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString()
            val confirmPassword = binding.etConfirmPassword.text.toString()

            //if any of the fields data input is empty then return same listener
            if (name.isEmpty() ||
                email.isEmpty() ||
                password.isEmpty() ||
                confirmPassword.isEmpty()
            ) {
                return@setOnClickListener
            }

            //password and confirm password should be same
            if (password != confirmPassword) {
                return@setOnClickListener
            }

            viewModel.registerUser(
                name = name,
                email = email,
                password = password
            )

        }
        observeUiState()
    }

    private fun observeUiState(){
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(
                Lifecycle.State.STARTED
            ){
                viewModel.uiState.collect {state ->
                    when(state){
                        RegisterUiState.Idle -> {
                            // Nothing to do
                        }

                        RegisterUiState.Loading -> {
                            binding.btnRegister.isEnabled = false
                            binding.btnRegister.text = "REGISTERING..."
                        }

                        RegisterUiState.Success -> {
                            binding.btnRegister.isEnabled = true
                            binding.btnRegister.text = "REGISTER"
                            findNavController().navigate(
                                R.id.action_registerFragment_to_loginFragment
                            )
                        }

                        RegisterUiState.EmailAlreadyExists -> {
                            binding.btnRegister.isEnabled = true
                            binding.btnRegister.text = "REGISTER"

                            binding.emailLayout.error =
                                "An account with this email already exists"
                        }

                        is RegisterUiState.Error -> {
                            binding.btnRegister.isEnabled = true
                            binding.btnRegister.text = "REGISTER"

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