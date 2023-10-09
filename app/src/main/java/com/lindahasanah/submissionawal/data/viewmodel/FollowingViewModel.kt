package com.lindahasanah.submissionawal.data.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lindahasanah.submissionawal.data.response.FollowResponseItem
import com.lindahasanah.submissionawal.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingViewModel : ViewModel() {

    val listFollowing = MutableLiveData<ArrayList<FollowResponseItem>>()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun setListFollowing(username: String) {
        _isLoading.value = true
        ApiConfig.getApiService().getFollowingUser(username)
            .enqueue(object : Callback<ArrayList<FollowResponseItem>> {

                override fun onResponse(
                    call: Call<ArrayList<FollowResponseItem>>,
                    response: Response<ArrayList<FollowResponseItem>>
                ) {
                    _isLoading.value = false
                    if (response.isSuccessful) {
                        listFollowing.postValue(response.body())
                    }

                }

                override fun onFailure(call: Call<ArrayList<FollowResponseItem>>, t: Throwable) {
                    _isLoading.value = false
                    Log.d("Failure", t.message!!)
                }
            })
    }


    fun getListFollowing(): LiveData<ArrayList<FollowResponseItem>> {
        return listFollowing
    }
}