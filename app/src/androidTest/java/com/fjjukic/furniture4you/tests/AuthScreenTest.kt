package com.fjjukic.furniture4you.tests

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
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
class AuthScreenTest {
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
    fun checkLoginUi() {
        val activity = composeTestRule.activity

        // Check header displayed
        composeTestRule
            .onNodeWithText(activity.getString(R.string.mock_title_header))
            .assertIsDisplayed()
        composeTestRule
            .onNodeWithText(activity.getString(R.string.mock_subtitle_header).uppercase())
            .assertIsDisplayed()

        // Check login form displayed (both title and field placeholder)
        composeTestRule
            .onAllNodesWithText(activity.getString(R.string.field_email))
            .assertCountEquals(2)
        composeTestRule
            .onAllNodesWithText(activity.getString(R.string.field_password))
            .assertCountEquals(2)

        // Check buttons displayed
        composeTestRule
            .onNodeWithText(activity.getString(R.string.btn_forgot_password))
            .assertIsDisplayed()
        composeTestRule
            .onNodeWithText(activity.getString(R.string.btn_login))
            .assertIsDisplayed()
        composeTestRule
            .onNodeWithText(activity.getString(R.string.btn_sign_up).uppercase())
            .assertIsDisplayed()
    }

    @Test
    fun checkSignUpUi() {
        val activity = composeTestRule.activity

        // Navigate to Register screen
        composeTestRule
            .onNodeWithText(activity.getString(R.string.btn_sign_up).uppercase())
            .performClick()
        composeTestRule.waitForIdle()

        // Check header displayed
        composeTestRule
            .onNodeWithText(activity.getString(R.string.title_register).uppercase())
            .assertIsDisplayed()

        // Check register form displayed (both title and field placeholder)
        composeTestRule
            .onNodeWithText(activity.getString(R.string.field_name))
            .assertIsDisplayed()
        composeTestRule
            .onNodeWithText(activity.getString(R.string.placeholder_name))
            .assertIsDisplayed()
        composeTestRule
            .onAllNodesWithText(activity.getString(R.string.field_email))
            .assertCountEquals(2)
        composeTestRule
            .onAllNodesWithText(activity.getString(R.string.field_password))
            .assertCountEquals(2)
        composeTestRule
            .onNodeWithText(activity.getString(R.string.field_confirm_password))
            .assertIsDisplayed()
        composeTestRule
            .onNodeWithText(activity.getString(R.string.placeholder_password))
            .assertIsDisplayed()

        // Check buttons displayed
        composeTestRule
            .onNodeWithText(activity.getString(R.string.btn_sign_up).uppercase())
            .assertIsDisplayed()
        composeTestRule
            .onNodeWithText(
                "${activity.getString(R.string.label_register_have_account)} ${
                    activity.getString(
                        R.string.btn_login
                    ).uppercase()
                }"
            )
            .assertIsDisplayed()
    }

    @Test
    fun checkResetPasswordUi() {
        val activity = composeTestRule.activity

        // Navigate to Reset password screen
        composeTestRule
            .onNodeWithText(activity.getString(R.string.btn_forgot_password))
            .performClick()
        composeTestRule.waitForIdle()


        // Check header displayed
        composeTestRule
            .onNodeWithText(activity.getString(R.string.title_reset_password).uppercase())
            .assertIsDisplayed()

        // Check login form displayed (both title and field placeholder)
        composeTestRule
            .onAllNodesWithText(activity.getString(R.string.field_email))
            .assertCountEquals(2)

        // Check buttons displayed
        composeTestRule
            .onNodeWithText(activity.getString(R.string.btn_send_email))

        val rememberPasswordText = activity.getString(R.string.btn_remember_your_password)
        val loginText = activity.getString(R.string.btn_login).uppercase()
        composeTestRule
            .onNodeWithText("$rememberPasswordText $loginText")
            .assertIsDisplayed()
    }
}