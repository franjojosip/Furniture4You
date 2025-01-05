package com.fjjukic.furniture4you.viewmodels

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.fjjukic.furniture4you.MainActivity
import com.fjjukic.furniture4you.ui.common.prelogin.PreloginViewModel
import com.fjjukic.furniture4you.ui.common.repository.MainRepository
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import junit.framework.TestCase.assertFalse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.runner.RunWith
import javax.inject.Inject

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class PreloginViewModelTest {

    private val hiltRule = HiltAndroidRule(this)
    private val composeTestRule = createAndroidComposeRule<MainActivity>()
    private val testDispatcher = StandardTestDispatcher()


    private lateinit var viewModel: PreloginViewModel

    @get:Rule
    val rule: RuleChain = RuleChain
        .outerRule(hiltRule)
        .around(composeTestRule)

    @Inject
    lateinit var repository: MainRepository

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        hiltRule.inject()
        Dispatchers.setMain(testDispatcher)
        viewModel = PreloginViewModel(repository)
    }

    @Test
    fun testDefaultValues() = runTest {
        val initialValue = viewModel.preloginShown.first()
        assertFalse(initialValue)
    }
//
//    @Test
//    @Throws(InterruptedException::class)
//    fun testAfterPreloginShow(): Unit = runTest {
//        viewModel.onPreloginShown()
//        assertTrue(viewModel.preloginShown.value)
//    }
}
