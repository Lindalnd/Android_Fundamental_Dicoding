package com.lindahasanah.submissionawal.data.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lindahasanah.submissionawal.data.response.DetailUserResponse
import com.lindahasanah.submissionawal.data.retrofit.ApiConfig
import com.lindahasanah.submissionawal.database.FavDao
import com.lindahasanah.submissionawal.database.FavoriteDatabase
import com.lindahasanah.submissionawal.database.FavoriteUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application) : AndroidViewModel(application) {

    private val _userDetail = MutableLiveData<DetailUserResponse>()
    val userDetail: LiveData<DetailUserResponse> = _userDetail

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var favDao: FavDao? = null
    private var favDB: FavoriteDatabase? = null


    init {
        favDB = FavoriteDatabase.getDataase(application)
        favDao = favDB?.favoriteDao()
    }


    companion object {
        private const val TAG = "DetailViewModel"
    }

    fun setUserDetail(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUser(username)
        client.enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>, response: Response<DetailUserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _userDetail.value = response.body()?.copy()

                } else {
                    Log.e(TAG, "onFailure : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure : ${t.message}")
            }
        })
    }

    fun addToFav(username: String, id: Int, avatarUrl: String) {

        CoroutineScope(Dispatchers.IO).launch {
            val user = FavoriteUser(
                username, id, avatarUrl
            )
            favDao?.insert(user)

        }

    }

    suspend fun checkUser(id: Int) = favDao?.checkFav(id)

    fun deleteFav(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            favDao?.deleteFromFav(id)

        }

    }
}