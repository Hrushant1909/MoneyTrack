package com.hrushant.moneytrack.ui.transaction

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.hrushant.moneytrack.R
import com.hrushant.moneytrack.data.entity.TransactionType
import com.hrushant.moneytrack.data.local.database.DatabaseProvider
import com.hrushant.moneytrack.data.local.session.SessionManager
import com.hrushant.moneytrack.data.repository.CategoryRepository
import com.hrushant.moneytrack.data.repository.TransactionRepository
import com.hrushant.moneytrack.databinding.FragmentAddTransactionBinding
import kotlinx.coroutines.launch

class AddTransactionFragment :
    Fragment(R.layout.fragment_add_transaction) {

    private var _binding: FragmentAddTransactionBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AddTransactionViewModel by viewModels {

        val database = DatabaseProvider.getDatabase(requireContext())
        val categoryRepository = CategoryRepository(database.categoryDao())
        val transactionRepository = TransactionRepository(database.transactionDao())
        val sessionManager = SessionManager(requireContext())

        AddTransactionViewModelFactory(categoryRepository, transactionRepository, sessionManager)
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentAddTransactionBinding.bind(view)

        observeCategories()

        // Initially show expense categories
        viewModel.loadCategories(
            TransactionType.EXPENSE
        )

        binding.rgType.setOnCheckedChangeListener { _, checkedId ->

            when (checkedId) {

                binding.rbExpense.id -> {
                    viewModel.loadCategories(
                        TransactionType.EXPENSE
                    )
                }

                binding.rbIncome.id -> {
                    viewModel.loadCategories(
                        TransactionType.INCOME
                    )
                }
            }
        }

        binding.btnAddTransaction.setOnClickListener {
            val amountText = binding.etAmount.text.toString().trim()
            val description = binding.etDescription.text.toString().trim()
            if(amountText.isEmpty()){
                binding.amountLayout.error = "Enter an amount"
                return@setOnClickListener
            }

            val amount = amountText.toDoubleOrNull()

            if (amount == null || amount <= 0) {
                binding.amountLayout.error = "Enter a valid amount"
                return@setOnClickListener
            }

            binding.amountLayout.error = null

            val type =
                if (binding.rbExpense.isChecked) {
                    TransactionType.EXPENSE
                } else {
                    TransactionType.INCOME
                }

            val selectedCategory =
                viewModel.categories.value.getOrNull(
                    binding.spinnerCategory.selectedItemPosition
                )

            if (selectedCategory == null) {
                return@setOnClickListener
            }

            viewModel.saveTransaction(
                amount = amount,
                type = type,
                categoryId = selectedCategory.id,
                description = description
            )
        }

    }

    private fun observeCategories() {

        viewLifecycleOwner.lifecycleScope.launch {

            viewLifecycleOwner.repeatOnLifecycle(
                Lifecycle.State.STARTED
            ) {

                viewModel.categories.collect { categories ->

                    val categoryNames =
                        categories.map { it.name }

                    val adapter =
                        ArrayAdapter(
                            requireContext(),
                            android.R.layout.simple_spinner_item,
                            categoryNames
                        )

                    adapter.setDropDownViewResource(
                        android.R.layout.simple_spinner_dropdown_item
                    )

                    binding.spinnerCategory.adapter = adapter
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}