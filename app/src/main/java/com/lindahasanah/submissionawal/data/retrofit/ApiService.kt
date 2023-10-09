package com.lindahasanah.submissionawal.data.retrofit

import com.lindahasanah.submissionawal.data.response.DetailUserResponse
import com.lindahasanah.submissionawal.data.response.FollowResponse
import com.lindahasanah.submissionawal.data.response.FollowResponseItem
import com.lindahasanah.submissionawal.data.response.GithubResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

//    @Headers("Authorization: token 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")
    @GET("search/users")
    fun getSearchUsers(@Query("q") query: String): Call<GithubResponse>

    @GET("users/{username}")
    fun getDetailUser(@Path("username") username: String): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    fun getFollowersUser(@Path("username") username: String): Call<ArrayList<FollowResponseItem>>

    @GET("users/{username}/following")
    fun getFollowingUser(@Path("username") username: String): Call<ArrayList<FollowResponseItem>>
}
