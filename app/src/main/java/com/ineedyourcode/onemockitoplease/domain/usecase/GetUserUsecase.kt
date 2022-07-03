package com.ineedyourcode.onemockitoplease.domain.usecase

import com.ineedyourcode.onemockitoplease.domain.entity.UserProfile

interface GetUserUsecase {
    fun getUser(userName: String, callback: GetUserCallback)
}

interface GetUserCallback {
    fun onSuccess(userProfile: UserProfile)
    fun onError(error: Throwable)
}