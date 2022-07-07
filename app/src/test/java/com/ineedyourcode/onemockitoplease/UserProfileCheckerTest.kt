package com.ineedyourcode.onemockitoplease

import com.ineedyourcode.onemockitoplease.ui.utils.UserProfileChecker
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class UserProfileCheckerTest {
    private val checker = UserProfileChecker()

    @Test
    fun emptyLogin_True() {
        assertTrue(checker.checkEmptyRequest(""))
    }

    @Test
    fun emptyLogin_False() {
        assertFalse(checker.checkEmptyRequest("1"))
    }

    @Test
    fun jakeWhartonCheck_True() {
        assertTrue(checker.checkIsItJakeWhartonProfile("66577"))
    }

    @Test
    fun jakeWhartonCheck_False() {
        assertFalse(checker.checkIsItJakeWhartonProfile(""))
    }
}
