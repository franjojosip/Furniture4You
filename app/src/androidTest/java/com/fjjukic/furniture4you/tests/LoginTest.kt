package com.fjjukic.furniture4you.tests

import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.fjjukic.furniture4you.MainActivity
import com.fjjukic.furniture4you.R
import com.fjjukic.furniture4you.ui.common.repository.MainRepository
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class LoginTest {
    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Inject
    lateinit var mainRepository: MainRepository

    private fun navigateToLogin() {
        if (mainRepository.getShowPrelogin()) {
            mainRepository.onPreloginShown()

            composeTestRule
                .onNodeWithText(composeTestRule.activity.getString(R.string.btn_get_started))
                .performClick()
            composeTestRule.waitForIdle()
        }
    }

    @Before
    fun beforeTest() {
        hiltRule.inject()
        navigateToLogin()
    }

    @Test
    fun testLoginWithMockData() {
        composeTestRule
            .onNodeWithTag("emailField")
            .performTextInput("test@mail.com")

        composeTestRule
            .onNodeWithTag("passwordField")
            .performTextInput("test123")

        composeTestRule
            .onNodeWithText(composeTestRule.activity.getString(R.string.btn_login))
            .performClick()

        composeTestRule.waitForIdle()

        composeTestRule.waitUntil(3000) {
            composeTestRule
                .onNodeWithTag("homeTitle")
                .isDisplayed()
        }
    }
}