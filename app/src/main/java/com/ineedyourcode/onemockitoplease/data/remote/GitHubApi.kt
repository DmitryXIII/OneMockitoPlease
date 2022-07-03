package com.ineedyourcode.onemockitoplease.data.remote

import com.ineedyourcode.onemockitoplease.data.remote.dto.UserProfileDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubApi {
    @GET("users/{user}")
    fun getGitHubUser(@Path("user") login: String): Call<UserProfileDto>
}