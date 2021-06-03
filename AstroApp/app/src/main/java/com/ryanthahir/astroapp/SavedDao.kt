package com.ryanthahir.astroapp

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface SavedDao {
    @Query("Select * from savedThings")
    fun getSaveds(): LiveData<List<Saved>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertSaved(saved: Saved)

    @Delete
    suspend fun deleteSaved(saved: Saved)

    @Update
    suspend fun updateSaved(saved: Saved)
}