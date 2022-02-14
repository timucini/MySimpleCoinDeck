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
import com.example.mysimplecoindeck.data.FakeResponseDataAndroid.coinRankingEntity
import com.example.mysimplecoindeck.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import javax.inject.Inject

/*
 * MediumTest -> Integration Test
 */

@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class CoinsRankingFragmentTest {

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
        launchFragmentInHiltContainer<CoinsRankingFragment>(fragmentFactory = fragmentFactory) {
            Navigation.setViewNavController(requireView(), navController)
        }
        onView(withId(R.id.coinRankingFragmentLayout)).check(matches(isDisplayed()))
    }

    @Test
    fun clickOnCoinItem_navigateToSingleCoinFragment() {
        val navController = mock(NavController::class.java)
        launchFragmentInHiltContainer<CoinsRankingFragment>(fragmentFactory = fragmentFactory) {
            // use that mock NavController using Mockito
            Navigation.setViewNavController(requireView(), navController)
            coinsAdapter.coins = listOf(coinRankingEntity)
        }
        // use Espresso to test click on CoinRanking Item -> espresso contrib
        onView(withId(R.id.rvCoinsRanking))
            .perform(
               actionOnItemAtPosition<CoinsViewHolder>(
                0,
                click()))

        verify(navController).navigate(
            CoinsRankingFragmentDirections.actionCoinRankingFragmentToSingleCoinFragment("btc")
        )
    }
}