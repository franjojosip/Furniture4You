package com.fjjukic.furniture4you

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class PreloginScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun checkUiAndNavigate() {
        val activity = composeTestRule.activity

        // Check UI text
        composeTestRule
            .onNodeWithText(activity.getString(R.string.title_prelogin), ignoreCase = true)
            .assertIsDisplayed()
        composeTestRule
            .onNodeWithText(activity.getString(R.string.subtitle_prelogin), ignoreCase = true)
            .assertIsDisplayed()
        composeTestRule
            .onNodeWithText(
                activity.getString(R.string.label_prelogin_description),
                ignoreCase = true
            )
            .assertIsDisplayed()

        // Check UI navigation to next screen
        composeTestRule
            .onNodeWithText(activity.getString(R.string.btn_get_started))
            .performClick()
    }
}