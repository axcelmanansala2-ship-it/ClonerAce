package com.appcloner.presentation.fragments

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import com.appcloner.R
import com.appcloner.data.model.AppInfo
import com.appcloner.databinding.FragmentAllAppsBinding
import com.appcloner.domain.model.Result
import com.appcloner.presentation.adapter.AppListAdapter
import com.appcloner.presentation.dialogs.CloneNameDialog
import com.appcloner.presentation.viewmodel.CloneViewModel
import com.appcloner.presentation.viewmodel.MainViewModel
import com.appcloner.utils.extensions.gone
import com.appcloner.utils.extensions.setVisible
import com.appcloner.utils.extensions.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AllAppsFragment : Fragment() {
    private var _binding: FragmentAllAppsBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel: MainViewModel by activityViewModels()
    private val cloneViewModel: CloneViewModel by activityViewModels()
    private lateinit var adapter: AppListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentAllAppsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        setupMenu()
        observeApps()
    }

    private fun setupAdapter() {
        adapter = AppListAdapter { app -> showCloneDialog(app) }
        binding.recyclerViewApps.adapter = adapter
    }

    private fun setupMenu() {
        (requireActivity() as MenuHost).addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, inflater: MenuInflater) {
                inflater.inflate(R.menu.menu_apps, menu)
                val searchItem = menu.findItem(R.id.action_search)
                val searchView = searchItem.actionView as SearchView
                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(q: String) = false
                    override fun onQueryTextChange(q: String): Boolean { mainViewModel.search(q); return true }
                })
            }
            override fun onMenuItemSelected(item: MenuItem): Boolean {
                return when (item.itemId) {
                    R.id.sort_name -> { mainViewModel.sortByName(); true }
                    R.id.sort_size -> { mainViewModel.sortBySize(); true }
                    R.id.sort_date -> { mainViewModel.sortByDate(); true }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun observeApps() {
        mainViewModel.apps.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> { binding.progressBar.visible(); binding.recyclerViewApps.gone() }
                is Result.Success -> {
                    binding.progressBar.gone(); binding.recyclerViewApps.visible()
                    adapter.submitList(result.data)
                    binding.emptyView.setVisible(result.data.isEmpty())
                }
                is Result.Error -> { binding.progressBar.gone() }
            }
        }
    }

    private fun showCloneDialog(app: AppInfo) {
        CloneNameDialog.newInstance(app.packageName, app.appName) { name ->
            cloneViewModel.cloneApp(app.packageName, name)
        }.show(childFragmentManager, "clone_dialog")
    }

    override fun onDestroyView() { super.onDestroyView(); _binding = null }
}
