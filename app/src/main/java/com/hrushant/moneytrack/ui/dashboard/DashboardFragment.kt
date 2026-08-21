package com.hrushant.moneytrack.ui.dashboard

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.hrushant.moneytrack.R
import com.hrushant.moneytrack.data.local.database.DatabaseProvider
import com.hrushant.moneytrack.data.repository.CategoryRepository
import com.hrushant.moneytrack.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment(R.layout.fragment_dashboard) {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DashboardViewModel by viewModels {

        val database = DatabaseProvider.getDatabase(requireContext())
        val repository = CategoryRepository(database.categoryDao())

        DashboardViewModelFactory(repository)
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentDashboardBinding.bind(view)

        viewModel.initializeCategories()

        binding.btnAddTransaction.setOnClickListener {
            findNavController().navigate(
                R.id.action_dashboardFragment_to_addTransactionFragment
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}