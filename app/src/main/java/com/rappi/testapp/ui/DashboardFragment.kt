package com.rappi.testapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.rappi.testapp.R
import com.rappi.testapp.databinding.DashboardFragmentBinding

class DashboardFragment : Fragment() {

    private lateinit var binding: DashboardFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = DashboardFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupBottomNavigationMenu()
    }

    private fun setupBottomNavigationMenu() {
        binding.bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.item1 -> {
                    Toast.makeText(
                        requireContext(),
                        "Selected navigation item 1",
                        Toast.LENGTH_SHORT
                    ).show()
                    true
                }
                R.id.item2 -> {
                    Toast.makeText(
                        requireContext(),
                        "Selected navigation item 2",
                        Toast.LENGTH_SHORT
                    ).show()
                    true
                }
                R.id.item3 -> {
                    Toast.makeText(
                        requireContext(),
                        "Selected navigation item 3",
                        Toast.LENGTH_SHORT
                    ).show()
                    true
                }
                else -> false
            }
        }
    }
}