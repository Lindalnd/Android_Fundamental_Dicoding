package com.lindahasanah.submissionawal.data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lindahasanah.submissionawal.database.FavDao
import com.lindahasanah.submissionawal.database.FavoriteDatabase
import com.lindahasanah.submissionawal.database.FavoriteUser

class FavoriteViewModel(application: Application): AndroidViewModel(application) {

    private var favDao: FavDao? = null
    private var favDB: FavoriteDatabase? = null

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        favDB = FavoriteDatabase.getDataase(application)
        favDao = favDB?.favoriteDao()
    }

    fun getFavorite():LiveData<List<FavoriteUser>>?{
        _isLoading.value = false
        return favDao?.getFavorite()
    }
}