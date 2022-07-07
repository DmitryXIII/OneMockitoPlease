package com.ineedyourcode.onemockitoplease.ui.utils


private const val JAKE_WHARTON_PROFILE_ID = "66577"

class UserProfileChecker {
    fun checkEmptyRequest(request: String): Boolean {
        return request.isEmpty()
    }

    fun checkIsItJakeWhartonProfile(userProfileId: String): Boolean {
        return userProfileId == JAKE_WHARTON_PROFILE_ID
    }
}