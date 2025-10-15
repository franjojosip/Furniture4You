package com.fjjukic.furniture4you.tests.main

import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.fjjukic.furniture4you.MainActivity
import com.fjjukic.furniture4you.R
import com.fjjukic.furniture4you.ui.main.favorite.FavoriteScreen
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class FavoriteScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        hiltRule.inject()
        composeTestRule.activity.setContent {
            FavoriteScreen()
        }
    }

    @Test
    fun testFavoriteScreenElementsDisplayed() {
        composeTestRule.onNodeWithTag("favoriteTitle")
            .assertIsDisplayed()

        val btnAddAllToMyCart = composeTestRule.activity.getString(R.string.btn_add_all_to_my_cart)
        composeTestRule.onNodeWithText(btnAddAllToMyCart)
            .assertIsDisplayed()
    }
}