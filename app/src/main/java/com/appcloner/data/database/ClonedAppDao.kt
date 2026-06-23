package com.appcloner.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.appcloner.data.model.ClonedApp

@Dao
interface ClonedAppDao {
    @Query("SELECT * FROM cloned_apps")
    fun getAllClonedApps(): LiveData<List<ClonedApp>>

    @Query("SELECT * FROM cloned_apps WHERE originalPackage = :packageName")
    suspend fun getClonesByOriginalPackage(packageName: String): List<ClonedApp>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(clonedApp: ClonedApp)

    @Delete
    suspend fun delete(clonedApp: ClonedApp)

    @Query("DELETE FROM cloned_apps WHERE cloneId = :cloneId")
    suspend fun deleteById(cloneId: String)

    @Query("SELECT EXISTS(SELECT 1 FROM cloned_apps WHERE originalPackage = :packageName)")
    suspend fun isCloned(packageName: String): Boolean
}
