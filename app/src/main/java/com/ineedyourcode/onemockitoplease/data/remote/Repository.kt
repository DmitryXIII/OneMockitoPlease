package com.ineedyourcode.onemockitoplease.data.remote

import com.ineedyourcode.onemockitoplease.data.remote.dto.UserMapper
import com.ineedyourcode.onemockitoplease.data.remote.dto.UserProfileDto
import com.ineedyourcode.onemockitoplease.domain.usecase.GetUserCallback
import com.ineedyourcode.onemockitoplease.domain.usecase.GetUserUsecase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Repository(
    private val remoteDataSource: GitHubApi,
    private val mapper: UserMapper,
) : GetUserUsecase {
    override fun getUser(userName: String, callback: GetUserCallback) {
        remoteDataSource.getGitHubUser(userName).enqueue(object : Callback<UserProfileDto> {
            override fun onResponse(
                call: Call<UserProfileDto>,
                response: Response<UserProfileDto>,
            ) {
                if (response.body() == null) {
                    callback.onError(NullPointerException("Пользователь не существует"))
                } else {
                    response.body()?.let {
                        callback.onSuccess(mapper.mapUserProfileDto(it))
                    }
                }
            }

            override fun onFailure(call: Call<UserProfileDto>, t: Throwable) {
                callback.onError(t)
            }
        })
    }
}