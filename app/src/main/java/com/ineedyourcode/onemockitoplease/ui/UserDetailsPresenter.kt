package com.ineedyourcode.onemockitoplease.ui

import com.ineedyourcode.onemockitoplease.domain.entity.UserProfile
import com.ineedyourcode.onemockitoplease.domain.usecase.GetUserCallback
import com.ineedyourcode.onemockitoplease.domain.usecase.GetUserUsecase
import com.ineedyourcode.onemockitoplease.ui.utils.UserProfileChecker

class UserDetailsPresenter(
    private val repository: GetUserUsecase,
    private val userProfileChecker: UserProfileChecker,
) : PresenterContract {
    private var view: ViewContract? = null

    override fun onAttach(view: ViewContract) {
        this.view = view
    }

    override fun onDetach() {
        view = null
    }

    override fun getUserProfile(userName: String) {
        if (userProfileChecker.checkEmptyRequest(userName)) {
            view?.showError("Логин не может быть пустым")
        } else {
            view?.showProgress()
            repository.getUser(userName, object : GetUserCallback {
                override fun onSuccess(userProfile: UserProfile) {
                    view?.hideProgress()
                    if (userProfileChecker.checkIsItJakeWhartonProfile(userProfile)) {
                        view?.showResult(userProfile)
                    } else {
                        view?.showError("Это не Jake Wharton")
                    }
                }

                override fun onError(error: Throwable) {
                    view?.hideProgress()
                    view?.showError(error.message.toString())
                }
            })
        }
    }
}