package com.hrushant.moneytrack.ui.dashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.hrushant.moneytrack.R
import com.hrushant.moneytrack.data.local.database.DatabaseProvider
import com.hrushant.moneytrack.data.repository.CategoryRepository


class DashboardFragment : Fragment(R.layout.fragment_dashboard) {

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

        viewModel.initializeCategories()
    }
}