package com.appcloner.data.repository

import com.appcloner.data.database.ClonedAppDao
import com.appcloner.data.model.ClonedApp
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CloneRepository @Inject constructor(
    private val clonedAppDao: ClonedAppDao
) {
    fun getAllClonedApps(): Flow<List<ClonedApp>> = clonedAppDao.getAllClonedApps()

    suspend fun getClonedApp(cloneId: String): ClonedApp? = clonedAppDao.getClonedApp(cloneId)

    suspend fun addClonedApp(clonedApp: ClonedApp) = clonedAppDao.insertClonedApp(clonedApp)

    suspend fun removeClonedApp(clonedApp: ClonedApp) = clonedAppDao.deleteClonedApp(clonedApp)

    suspend fun updateClonedApp(clonedApp: ClonedApp) = clonedAppDao.updateClonedApp(clonedApp)

    /** Returns the next free virtual-user slot for a given package (supports multiple clones). */
    suspend fun getNextUserSlot(packageName: String): Int {
        val existing = clonedAppDao.getClonedAppsByOriginalPackage(packageName)
        val used = existing.map { it.userHandle }.toSet()
        var slot = 0
        while (used.contains(slot)) slot++
        return slot
    }
}
