package com.appcloner.data.database

import androidx.room.*
import com.appcloner.data.model.ClonedApp
import kotlinx.coroutines.flow.Flow

@Dao
interface ClonedAppDao {

    @Query("SELECT * FROM cloned_apps ORDER BY installDate DESC")
    fun getAllClonedApps(): Flow<List<ClonedApp>>

    @Query("SELECT * FROM cloned_apps WHERE cloneId = :cloneId LIMIT 1")
    suspend fun getClonedApp(cloneId: String): ClonedApp?

    @Query("SELECT * FROM cloned_apps WHERE originalPackage = :packageName")
    suspend fun getClonedAppsByOriginalPackage(packageName: String): List<ClonedApp>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertClonedApp(clonedApp: ClonedApp)

    @Update
    suspend fun updateClonedApp(clonedApp: ClonedApp)

    @Delete
    suspend fun deleteClonedApp(clonedApp: ClonedApp)

    @Query("DELETE FROM cloned_apps WHERE cloneId = :cloneId")
    suspend fun deleteById(cloneId: String)

    @Query("SELECT EXISTS(SELECT 1 FROM cloned_apps WHERE originalPackage = :packageName)")
    suspend fun isCloned(packageName: String): Boolean
}
