package com.appcloner.presentation.fragments

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.appcloner.databinding.FragmentSettingsBinding
import com.appcloner.presentation.viewmodel.SettingsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SettingsViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.isDarkMode.observe(viewLifecycleOwner) { dark ->
            binding.switchDarkMode.isChecked = dark
        }
        viewModel.isRootAvailable.observe(viewLifecycleOwner) { root ->
            binding.tvRootStatus.text = if (root) "Root: Available" else "Root: Not available"
        }
        viewModel.isGameGuardianInstalled.observe(viewLifecycleOwner) { installed ->
            binding.btnGameGuardian.text = if (installed) "Open Game Guardian" else "Download Game Guardian"
        }
        binding.switchDarkMode.setOnCheckedChangeListener { _, checked ->
            viewModel.toggleDarkMode()
            AppCompatDelegate.setDefaultNightMode(
                if (checked) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
            )
        }
        binding.btnCheckRoot.setOnClickListener { viewModel.checkRoot() }
        binding.btnGameGuardian.setOnClickListener { viewModel.openGameGuardian() }
    }

    override fun onDestroyView() { super.onDestroyView(); _binding = null }
}
