package com.ineedyourcode.onemockitoplease

import com.ineedyourcode.onemockitoplease.ui.utils.UserProfileChecker
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

private const val EMPTY_TEST_REQUEST = ""
private const val NOT_EMPTY_TEST_REQUEST = "1"
private const val JAKE_WHARTON_PROFILE_ID = "66577"

class UserProfileCheckerTest {
    private val checker = UserProfileChecker()

    @Test
    fun emptyLogin_True() {
        assertTrue(checker.checkEmptyRequest(EMPTY_TEST_REQUEST))
    }

    @Test
    fun emptyLogin_False() {
        assertFalse(checker.checkEmptyRequest(NOT_EMPTY_TEST_REQUEST))
    }

    @Test
    fun jakeWhartonCheck_True() {
        assertTrue(checker.checkIsItJakeWhartonProfile(JAKE_WHARTON_PROFILE_ID))
    }

    @Test
    fun jakeWhartonCheck_False() {
        assertFalse(checker.checkIsItJakeWhartonProfile(EMPTY_TEST_REQUEST))
    }
}
