package com.example.mysimplecoindeck.ui.fragments

import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.MediumTest
import com.example.mysimplecoindeck.R
import com.example.mysimplecoindeck.adapters.CoinsAdapter
import com.example.mysimplecoindeck.adapters.CoinsAdapter.*
import com.example.mysimplecoindeck.db.PortfolioDatabase
import com.example.mysimplecoindeck.launchFragmentInHiltContainer
import com.example.mysimplecoindeck.models.Coin
import com.example.mysimplecoindeck.repository.FakeAndroidCoinRepositoryImpl
import com.example.mysimplecoindeck.ui.CoinsViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import javax.inject.Inject
import javax.inject.Named

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
        val coin = Coin("24","1","1","google.de","red","google.de",4,
            false,"100","Bitcoin","40000",1, listOf(),"btc",1,"btc")
        launchFragmentInHiltContainer<CoinsRankingFragment>(fragmentFactory = fragmentFactory) {
            // use that mock NavController using Mockito
            Navigation.setViewNavController(requireView(), navController)
            coinsAdapter.coins = listOf(coin)
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