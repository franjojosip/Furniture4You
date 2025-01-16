package com.fjjukic.furniture4you.tests

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.fjjukic.furniture4you.MainActivity
import com.fjjukic.furniture4you.R
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class PreloginScreenTest {
    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun checkUiAndNavigate() {
        val activity = composeTestRule.activity

        // Check UI text
        composeTestRule
            .onNodeWithText(activity.getString(R.string.title_prelogin).uppercase())
            .assertIsDisplayed()
        composeTestRule
            .onNodeWithText(activity.getString(R.string.subtitle_prelogin).uppercase())
            .assertIsDisplayed()
        composeTestRule
            .onNodeWithText(activity.getString(R.string.label_prelogin_description))
            .assertIsDisplayed()

        // Check UI navigation to Login screen
        composeTestRule
            .onNodeWithText(activity.getString(R.string.btn_get_started))
            .performClick()

        composeTestRule.waitForIdle()

        composeTestRule
            .onNodeWithText(activity.getString(R.string.btn_login))
            .assertIsDisplayed()
    }
}