package com.hrushant.moneytrack.ui.dashboard

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.hrushant.moneytrack.R
import com.hrushant.moneytrack.data.local.database.DatabaseProvider
import com.hrushant.moneytrack.data.local.session.SessionManager
import com.hrushant.moneytrack.data.repository.CategoryRepository
import com.hrushant.moneytrack.data.repository.TransactionRepository
import com.hrushant.moneytrack.databinding.FragmentDashboardBinding
import com.hrushant.moneytrack.ui.transaction.TransactionAdapter
import kotlinx.coroutines.launch

class DashboardFragment : Fragment(R.layout.fragment_dashboard) {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    private lateinit var transactionAdapter: TransactionAdapter

    private val viewModel: DashboardViewModel by viewModels {

        val database = DatabaseProvider.getDatabase(requireContext())
        val categoryRepository =
            CategoryRepository(
                database.categoryDao()
            )

        val transactionRepository =
            TransactionRepository(
                database.transactionDao()
            )

        val sessionManager =
            SessionManager(requireContext())

        DashboardViewModelFactory(categoryRepository,
            transactionRepository,
            sessionManager
        )
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentDashboardBinding.bind(view)

        transactionAdapter = TransactionAdapter()

        binding.rvTransactions.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = transactionAdapter
        }

        observeTransactions()

        viewModel.initializeCategories()
        viewModel.loadTransactions()

        binding.btnAddTransaction.setOnClickListener {
            findNavController().navigate(
                R.id.action_dashboardFragment_to_addTransactionFragment
            )
        }
    }

    private fun observeTransactions() {

        viewLifecycleOwner.lifecycleScope.launch {

            viewLifecycleOwner.repeatOnLifecycle(
                Lifecycle.State.STARTED
            ) {

                viewModel.transactions.collect { transactions ->

                    transactionAdapter.submitList(transactions)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}