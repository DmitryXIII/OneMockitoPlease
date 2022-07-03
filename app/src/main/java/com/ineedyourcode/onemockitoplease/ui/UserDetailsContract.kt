package com.ineedyourcode.onemockitoplease.ui

import com.ineedyourcode.onemockitoplease.domain.entity.UserProfile

interface ViewContract {
     fun showResult(userProfile: UserProfile)
     fun showProgress()
     fun showError(message: String)
     fun hideProgress()
}

interface PresenterContract {
    fun onAttach(view: ViewContract)
    fun onDetach()
    fun getUserProfile(userName: String)
}