package com.ineedyourcode.onemockitoplease.ui

import com.ineedyourcode.onemockitoplease.domain.entity.UserProfile
import com.ineedyourcode.onemockitoplease.domain.usecase.GetUserCallback
import com.ineedyourcode.onemockitoplease.domain.usecase.GetUserUsecase

class UserDetailsPresenter(private val repository: GetUserUsecase): PresenterContract {
    private var view: ViewContract? = null

    override fun onAttach(view: ViewContract) {
        this.view = view
    }

    override fun onDetach() {
        view = null
    }

    override fun getUserProfile(userName: String) {
        view?.showProgress()
        repository.getUser(userName, object : GetUserCallback {
            override fun onSuccess(userProfile: UserProfile) {
                view?.hideProgress()
                view?.showResult(userProfile)
            }

            override fun onError(error: Throwable) {
                view?.hideProgress()
                view?.showError(error.message.toString())
            }
        })
    }
}