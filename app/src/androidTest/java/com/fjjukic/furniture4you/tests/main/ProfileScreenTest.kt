package com.fjjukic.furniture4you.tests.main

import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.fjjukic.furniture4you.MainActivity
import com.fjjukic.furniture4you.R
import com.fjjukic.furniture4you.ui.main.profile.ProfileScreen
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class ProfileScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        hiltRule.inject()
        composeTestRule.activity.setContent {
            ProfileScreen()
        }
    }

    @Test
    fun testProfileScreenElementsDisplayed() {
        val myOrdersText = composeTestRule.activity.getString(R.string.label_my_orders)
        val shippingAddressesText =
            composeTestRule.activity.getString(R.string.label_shipping_addresses)
        val paymentMethodText = composeTestRule.activity.getString(R.string.title_payment_method)
        val myReviewsText = composeTestRule.activity.getString(R.string.nav_my_reviews)
        val settingsText = composeTestRule.activity.getString(R.string.nav_settings)

        composeTestRule.onNodeWithTag("profileTitle").assertIsDisplayed()
        composeTestRule.onNodeWithTag("profileInformation").assertIsDisplayed()

        composeTestRule.onNodeWithText(myOrdersText).assertIsDisplayed()
        composeTestRule.onNodeWithText(shippingAddressesText).assertIsDisplayed()
        composeTestRule.onNodeWithText(paymentMethodText).assertIsDisplayed()
        composeTestRule.onNodeWithText(myReviewsText).assertIsDisplayed()
        composeTestRule.onNodeWithText(settingsText).assertIsDisplayed()
    }
}