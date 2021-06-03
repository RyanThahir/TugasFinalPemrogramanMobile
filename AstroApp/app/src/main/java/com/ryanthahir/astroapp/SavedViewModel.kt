package com.ryanthahir.astroapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class SavedViewModel (application: Application): AndroidViewModel(application){
    private var savedRepository = SavedRepository(application)
    private var saveds: LiveData<List<Saved>>? = savedRepository.getSaveds()

    fun insertSaved(saved: Saved) {
        savedRepository.insert(saved)
    }

    fun getSaveds(): LiveData<List<Saved>>? {
        return saveds
    }

    fun deleteSaved(saved: Saved) {
        savedRepository.delete(saved)
    }

    fun updateSaved(saved: Saved) {
        savedRepository.update(saved)
    }

}