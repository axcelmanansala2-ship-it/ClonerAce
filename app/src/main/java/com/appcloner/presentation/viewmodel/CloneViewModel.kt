package com.appcloner.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.appcloner.data.model.CloneStatus
import com.appcloner.data.model.ClonedApp
import com.appcloner.data.repository.CloneRepository
import com.appcloner.domain.usecase.CloneAppUseCase
import com.appcloner.domain.usecase.LaunchCloneUseCase
import com.appcloner.domain.usecase.UninstallCloneUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CloneViewModel @Inject constructor(
    private val cloneRepository: CloneRepository,
    private val cloneAppUseCase: CloneAppUseCase,
    private val launchCloneUseCase: LaunchCloneUseCase,
    private val uninstallCloneUseCase: UninstallCloneUseCase
) : ViewModel() {

    val clonedApps: LiveData<List<ClonedApp>> = cloneRepository.getAllClonedApps().asLiveData()

    private val _cloneStatus = MutableLiveData<CloneStatus>(CloneStatus.Idle)
    val cloneStatus: LiveData<CloneStatus> = _cloneStatus

    fun cloneApp(packageName: String, cloneName: String) {
        viewModelScope.launch {
            cloneAppUseCase(packageName, cloneName).collectLatest { status ->
                _cloneStatus.postValue(status)
            }
        }
    }

    fun launchClone(originalPackage: String, userHandle: Int) {
        launchCloneUseCase.invoke(originalPackage, userHandle)
    }

    fun uninstallClone(clonedApp: ClonedApp) {
        viewModelScope.launch {
            uninstallCloneUseCase.invoke(clonedApp)
        }
    }

    fun resetStatus() {
        _cloneStatus.value = CloneStatus.Idle
    }
}
