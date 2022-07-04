package com.ineedyourcode.onemockitoplease.ui.utils

import com.ineedyourcode.onemockitoplease.domain.entity.UserProfile

private const val JAKE_WHARTON_PROFILE_ID = "66577"

class UserProfileChecker {
    fun checkEmptyRequest(request: String): Boolean {
        return request.isEmpty()
    }

    fun checkIsItJakeWhartonProfile(userProfile: UserProfile): Boolean {
        return userProfile.id == JAKE_WHARTON_PROFILE_ID
    }
}