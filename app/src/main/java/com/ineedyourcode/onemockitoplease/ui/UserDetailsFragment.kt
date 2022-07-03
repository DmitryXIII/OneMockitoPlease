package com.ineedyourcode.onemockitoplease.ui

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import coil.load
import com.google.android.material.snackbar.Snackbar
import com.ineedyourcode.onemockitoplease.R
import com.ineedyourcode.onemockitoplease.domain.entity.UserProfile

class UserDetailsFragment : Fragment(R.layout.fragment_user_details), ViewContract {

    private lateinit var presenter: PresenterContract
    private lateinit var progressBar: ProgressBar
    private lateinit var userName: TextView
    private lateinit var userLogin: TextView
    private lateinit var userAvatar: ImageView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.onAttach(this)
        progressBar = view.findViewById(R.id.progress_bar)
        userName = view.findViewById(R.id.user_name_text_view)
        userLogin = view.findViewById(R.id.user_login_text_view)
        userAvatar = view.findViewById(R.id.user_avatar_image_view)
    }

    override fun showResult(userProfile: UserProfile) {
        userName.isVisible = true
        userName.text = userProfile.name
        userLogin.isVisible = true
        userLogin.text = userProfile.login
        userAvatar.isVisible = true
        userAvatar.load(userProfile.avatar)
    }

    override fun showProgress() {
        progressBar.isVisible = true
        userName.isVisible = false
        userLogin.isVisible = false
        userAvatar.isVisible = false
    }

    override fun showError(message: String) {
        this.view?.let { Snackbar.make(it, message, Snackbar.LENGTH_SHORT).show() }
    }

    override fun hideProgress() {
        progressBar.isVisible = false
    }

    override fun onDestroyView() {
        presenter.onDetach()
        super.onDestroyView()
    }
}