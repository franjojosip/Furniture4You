package com.fjjukic.furniture4you.tests.main

import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.fjjukic.furniture4you.MainActivity
import com.fjjukic.furniture4you.R
import com.fjjukic.furniture4you.ui.main.home.HomeScreen
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class HomeScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        hiltRule.inject()
        composeTestRule.activity.setContent {
            HomeScreen()
        }
    }

    @Test
    fun testHomeScreenElementsDisplayed() {
        composeTestRule.onNodeWithTag("homeHeader")
            .assertIsDisplayed()
        composeTestRule.onNodeWithTag("categoryFilter")
            .assertIsDisplayed()
        composeTestRule.onNodeWithTag("productList")
            .assertIsDisplayed()
    }

    @Test
    fun testHomeScreenBottomBarVisible() {
        val homeTabTitle = composeTestRule.activity.getString(R.string.nav_home)
        val favoritesTabTitle = composeTestRule.activity.getString(R.string.nav_favorites)
        val notificationTabTitle = composeTestRule.activity.getString(R.string.nav_notification)
        val profileTabTitle = composeTestRule.activity.getString(R.string.nav_profile)

        composeTestRule.onNodeWithContentDescription(homeTabTitle).assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription(favoritesTabTitle).assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription(notificationTabTitle).assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription(profileTabTitle).assertIsDisplayed()
    }
}