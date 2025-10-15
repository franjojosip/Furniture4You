package com.fjjukic.furniture4you


import app.cash.turbine.test
import com.fjjukic.furniture4you.ui.common.repository.FakeMainRepository
import com.fjjukic.furniture4you.ui.main.profile.ProfileViewModel
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ProfileViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var fakeRepository: FakeMainRepository
    private lateinit var viewModel: ProfileViewModel

    @Before
    fun setup() {
        fakeRepository = FakeMainRepository()
        viewModel = ProfileViewModel(fakeRepository)
    }

    @Test
    fun logoutTest() = runTest {
        viewModel.state.test {
            val initialState = awaitItem()
            assertTrue(initialState.isLoggedOut == null)

            viewModel.onLogoutClick()

            advanceTimeBy(400L)

            val updated = awaitItem()
            assertTrue(updated.isLoggedOut == true)

            cancelAndIgnoreRemainingEvents()
        }
    }
}