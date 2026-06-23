package com.appcloner.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appcloner.data.model.AppInfo
import com.appcloner.domain.model.Result
import com.appcloner.domain.usecase.GetInstalledAppsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getInstalledAppsUseCase: GetInstalledAppsUseCase
) : ViewModel() {

    private val _apps = MutableLiveData<Result<List<AppInfo>>>()
    val apps: LiveData<Result<List<AppInfo>>> = _apps

    private val _searchQuery = MutableLiveData<String>("")
    val searchQuery: LiveData<String> = _searchQuery

    private var allApps: List<AppInfo> = emptyList()

    init { loadApps() }

    fun loadApps() {
        _apps.value = Result.Loading
        viewModelScope.launch {
            val result = getInstalledAppsUseCase()
            if (result is Result.Success) allApps = result.data
            _apps.value = result
        }
    }

    fun search(query: String) {
        _searchQuery.value = query
        if (query.isEmpty()) {
            _apps.value = Result.Success(allApps)
        } else {
            val filtered = allApps.filter {
                it.appName.contains(query, ignoreCase = true) ||
                        it.packageName.contains(query, ignoreCase = true)
            }
            _apps.value = Result.Success(filtered)
        }
    }

    fun sortByName() {
        val data = (apps.value as? Result.Success)?.data ?: return
        _apps.value = Result.Success(data.sortedBy { it.appName })
    }

    fun sortBySize() {
        val data = (apps.value as? Result.Success)?.data ?: return
        _apps.value = Result.Success(data.sortedByDescending { it.sizeBytes })
    }

    fun sortByDate() {
        val data = (apps.value as? Result.Success)?.data ?: return
        _apps.value = Result.Success(data.sortedByDescending { it.installDate })
    }
}
