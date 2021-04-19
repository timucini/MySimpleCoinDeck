package com.example.mysimplecoindeck.ui.fragments

import android.os.Bundle
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.MediumTest
import com.example.mysimplecoindeck.R
import com.example.mysimplecoindeck.data.FakeResponseDataAndroid.singleCoin
import com.example.mysimplecoindeck.getOrAwaitValueTestAndroid
import com.example.mysimplecoindeck.launchFragmentInHiltContainer
import com.example.mysimplecoindeck.repository.FakeAndroidCoinRepositoryImpl
import com.example.mysimplecoindeck.ui.CoinsViewModel
import com.example.mysimplecoindeck.utils.Status
import com.google.common.truth.Truth
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import javax.inject.Inject

@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class SingleCoinFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var fragmentFactory: CoinsFragmentFactory

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun test_isFragmentInView() {
        val args = Bundle()
        args.putString("Uuid","Uuid")
        val navController = mock(NavController::class.java)
        launchFragmentInHiltContainer<SingleCoinFragment>(fragmentFactory = fragmentFactory, fragmentArgs = args) {
            Navigation.setViewNavController(requireView(), navController)
            navController.setGraph(R.id.singleCoinFragment, args)
        }

        onView(withId(R.id.singleCoinFragmentLayout)).check(matches(isDisplayed()))
    }

    @Test
    fun test_showDialogAndAddCoin() = runBlockingTest {
        val testViewModel = CoinsViewModel(FakeAndroidCoinRepositoryImpl())
        val args = Bundle()
        args.putString("Uuid","Uuid")
        val navController = mock(NavController::class.java)
        launchFragmentInHiltContainer<SingleCoinFragment>(fragmentFactory = fragmentFactory, fragmentArgs = args) {
            this.coin = singleCoin
            Navigation.setViewNavController(requireView(), navController)
            navController.setGraph(R.id.singleCoinFragment, args)
            viewModel = testViewModel
        }

        // Execute and verify
        onView(withId(R.id.fabAddCoin)).perform(ViewActions.click())

        onView(withText(R.string.submit)).check(matches(isDisplayed()))

        // Enter some Input
        onView(withId(R.id.md_input_message)).perform(typeText("10"))

        onView(withText(R.string.submit)).perform(ViewActions.click())

        val value = testViewModel.insertShoppingItemStatus.getOrAwaitValueTestAndroid()

        Truth.assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.SUCCESS)
    }

}