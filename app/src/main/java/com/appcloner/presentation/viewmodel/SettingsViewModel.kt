package com.appcloner.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.appcloner.data.local.PreferencesManager
import com.appcloner.service.GameGuardianHelper
import com.appcloner.service.RootCommandExecutor
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val preferencesManager: PreferencesManager,
    private val rootCommandExecutor: RootCommandExecutor,
    private val gameGuardianHelper: GameGuardianHelper
) : ViewModel() {

    private val _isDarkMode = MutableLiveData(preferencesManager.isDarkMode)
    val isDarkMode: LiveData<Boolean> = _isDarkMode

    private val _isRootAvailable = MutableLiveData(rootCommandExecutor.isRootAvailable())
    val isRootAvailable: LiveData<Boolean> = _isRootAvailable

    private val _isGameGuardianInstalled = MutableLiveData(gameGuardianHelper.isGameGuardianInstalled())
    val isGameGuardianInstalled: LiveData<Boolean> = _isGameGuardianInstalled

    fun toggleDarkMode() {
        val newValue = !preferencesManager.isDarkMode
        preferencesManager.isDarkMode = newValue
        _isDarkMode.value = newValue
    }

    fun checkRoot() {
        _isRootAvailable.value = rootCommandExecutor.isRootAvailable()
    }

    fun openGameGuardian() {
        if (gameGuardianHelper.isGameGuardianInstalled()) {
            gameGuardianHelper.launchGameGuardian()
        } else {
            gameGuardianHelper.openGGDownloadPage()
        }
    }
}
