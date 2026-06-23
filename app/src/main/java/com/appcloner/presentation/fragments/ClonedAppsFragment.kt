package com.appcloner.presentation.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.appcloner.databinding.FragmentClonedAppsBinding
import com.appcloner.presentation.adapter.ClonedAppAdapter
import com.appcloner.presentation.viewmodel.CloneViewModel
import com.appcloner.utils.extensions.gone
import com.appcloner.utils.extensions.setVisible
import com.appcloner.utils.extensions.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ClonedAppsFragment : Fragment() {
    private var _binding: FragmentClonedAppsBinding? = null
    private val binding get() = _binding!!
    private val cloneViewModel: CloneViewModel by activityViewModels()
    private lateinit var adapter: ClonedAppAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentClonedAppsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = ClonedAppAdapter(
            onLaunch = { app -> cloneViewModel.launchClone(app.clonedPackage, app.userHandle) },
            onDelete = { app -> cloneViewModel.uninstallClone(app) }
        )
        binding.recyclerViewClones.adapter = adapter
        cloneViewModel.clonedApps.observe(viewLifecycleOwner) { clones ->
            adapter.submitList(clones)
            binding.emptyView.setVisible(clones.isEmpty())
        }
    }

    override fun onDestroyView() { super.onDestroyView(); _binding = null }
}
