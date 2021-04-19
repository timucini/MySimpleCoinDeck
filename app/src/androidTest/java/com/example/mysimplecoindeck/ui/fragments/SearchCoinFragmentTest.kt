package com.example.mysimplecoindeck.ui.fragments

import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.MediumTest
import com.example.mysimplecoindeck.R
import com.example.mysimplecoindeck.adapters.CoinsAdapter.*
import com.example.mysimplecoindeck.launchFragmentInHiltContainer
import com.example.mysimplecoindeck.models.searchSuggestions.Coin
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import javax.inject.Inject

@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class SearchCoinFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var fragmentFactory: CoinsFragmentFactory


    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun test_isFragmentInView() {
        val navController = mock(NavController::class.java)
        launchFragmentInHiltContainer<SearchCoinFragment>(fragmentFactory = fragmentFactory) {
            Navigation.setViewNavController(requireView(), navController)
        }
        onView(withId(R.id.searchCoinFragmentLayout)).check(matches(isDisplayed()))
    }

    @Test
    fun clickOnCoinItem_navigateToSingleCoinFragment() {
        val navController = mock(NavController::class.java)
        val coin = Coin("google.de","bitcoin","btc","btc")
        launchFragmentInHiltContainer<SearchCoinFragment>(fragmentFactory = fragmentFactory) {
            Navigation.setViewNavController(requireView(), navController)
            searchAdapter.coins = listOf(coin)
        }
        onView(withId(R.id.rvSearch))
            .perform(
               actionOnItemAtPosition<CoinsViewHolder>(
                0,
                click()))

        verify(navController).navigate(
            SearchCoinFragmentDirections.actionSearchCoinFragmentToSingleCoinFragment("btc")
        )
    }
}