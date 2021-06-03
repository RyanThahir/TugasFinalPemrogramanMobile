package com.ryanthahir.astroapp

import android.app.Application
import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class SavedRepository(application: Application) {
    private val savedDao: SavedDao?
    private var saveds: LiveData<List<Saved>>? = null

    init {
        val db = AppDataBase.getInstance(application.applicationContext)
        savedDao = db?.savedDao()
        saveds = savedDao?.getSaveds()
    }

    fun getSaveds(): LiveData<List<Saved>>? {
        return saveds
    }

    fun insert(saved: Saved) = runBlocking {
        this.launch(Dispatchers.IO) {
            savedDao?.insertSaved(saved)
        }
    }

    fun delete(saved: Saved) {
        runBlocking {
            this.launch(Dispatchers.IO) {
                savedDao?.deleteSaved(saved)
            }
        }
    }

    fun update(saved: Saved) = runBlocking {
        this.launch(Dispatchers.IO) {
            savedDao?.updateSaved(saved)
        }
    }
}