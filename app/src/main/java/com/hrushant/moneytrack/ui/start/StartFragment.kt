package com.hrushant.moneytrack.ui.start

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.hrushant.moneytrack.R
import com.hrushant.moneytrack.data.local.session.SessionManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class StartFragment : Fragment(R.layout.fragment_start) {

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        val sessionManager = SessionManager(requireContext())

        viewLifecycleOwner.lifecycleScope.launch {

            val loggedIn = sessionManager.isLoggedIn.first()

            if (loggedIn) {

                findNavController().navigate(
                    R.id.action_startFragment_to_dashboardFragment
                )

            } else {

                findNavController().navigate(
                    R.id.action_startFragment_to_loginFragment
                )
            }
        }
    }
}