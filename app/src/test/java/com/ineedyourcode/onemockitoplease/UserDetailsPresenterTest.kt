package com.ineedyourcode.onemockitoplease

import com.ineedyourcode.onemockitoplease.domain.entity.UserProfile
import com.ineedyourcode.onemockitoplease.domain.usecase.GetUserUsecase
import com.ineedyourcode.onemockitoplease.ui.UserDetailsPresenter
import com.ineedyourcode.onemockitoplease.ui.ViewContract
import com.ineedyourcode.onemockitoplease.ui.utils.UserProfileChecker
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

private const val EMPTY_TEST_REQUEST = ""
private const val NOT_EMPTY_TEST_REQUEST = "1"
private const val EMPTY_TEST_REQUEST_ERROR = "Логин не может быть пустым"
private const val NOT_JAKE_WHARTON_ERROR = "Это не Jake Wharton"

class UserDetailsPresenterTest {
    private val repository = mock(GetUserUsecase::class.java)
    private val view = mock(ViewContract::class.java)
    private val checker = mock(UserProfileChecker::class.java)
    private val presenter = UserDetailsPresenter(repository, checker)

    @Before
    fun attachViewToPresenter() {
        presenter.onAttach(view)
    }

    @After
    fun detachViewFromPresenter() {
        presenter.onDetach()
    }

    @Test
    fun verify_view_showErrorOnEmptyRequest() {
        `when`(checker.checkEmptyRequest(EMPTY_TEST_REQUEST)).thenReturn(true)

        presenter.getUserProfile(EMPTY_TEST_REQUEST)

        verify(view, times(1)).showError(EMPTY_TEST_REQUEST_ERROR)
    }

    @Test
    fun verify_view_showProgress() {
        presenter.getUserProfile(NOT_EMPTY_TEST_REQUEST)

        verify(view, times(1)).showProgress()
    }

    @Test
    fun verify_repository_getUser() {
        presenter.getUserProfile(NOT_EMPTY_TEST_REQUEST)

        verify(repository).getUser(NOT_EMPTY_TEST_REQUEST, presenter)
    }

    @Test
    fun verify_onSuccess_view_hideProgress() {
        presenter.onSuccess(mock(UserProfile::class.java))

        verify(view).hideProgress()
    }

    @Test
    fun verify_onSuccess_view_showResult() {
        val userProfile = mock(UserProfile::class.java)

        `when`(checker.checkIsItJakeWhartonProfile(userProfile.id)).thenReturn(
            true
        )

        presenter.onSuccess(userProfile)

        verify(view).showResult(userProfile)
    }

    @Test
    fun verify_onSuccess_view_showError() {
        presenter.onSuccess(mock(UserProfile::class.java))

        verify(view).showError(NOT_JAKE_WHARTON_ERROR)
    }


    @Test
    fun verify_onError_view_hideProgress() {
        val error = mock(Throwable::class.java)
        presenter.onError(error)

        verify(view).hideProgress()
    }

    @Test
    fun verify_onError_view_showError() {
        val error = mock(Throwable::class.java)
        presenter.onError(error)

        verify(view).showError(error.message.toString())
    }
}


