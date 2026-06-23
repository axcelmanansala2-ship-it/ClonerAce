package com.appcloner.data.repository

import androidx.lifecycle.LiveData
import com.appcloner.data.database.ClonedAppDao
import com.appcloner.data.model.ClonedApp
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CloneRepository @Inject constructor(private val dao: ClonedAppDao) {

    fun getAllClonedApps(): LiveData<List<ClonedApp>> = dao.getAllClonedApps()

    suspend fun addClonedApp(clonedApp: ClonedApp) = dao.insert(clonedApp)

    suspend fun removeClonedApp(clonedApp: ClonedApp) = dao.delete(clonedApp)

    suspend fun removeClonedAppById(cloneId: String) = dao.deleteById(cloneId)

    suspend fun isCloned(packageName: String): Boolean = dao.isCloned(packageName)

    suspend fun getClonesForPackage(packageName: String): List<ClonedApp> =
        dao.getClonesByOriginalPackage(packageName)
}
