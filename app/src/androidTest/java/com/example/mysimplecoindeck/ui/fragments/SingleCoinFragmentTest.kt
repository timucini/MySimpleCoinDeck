package com.example.mysimplecoindeck.ui.fragments

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.example.mysimplecoindeck.R
import com.example.mysimplecoindeck.launchFragmentInHiltContainer
import com.example.mysimplecoindeck.repository.FakeAndroidCoinRepositoryImpl
import com.example.mysimplecoindeck.ui.CoinsViewModel
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
        val navController = mock(NavController::class.java)
        launchFragmentInHiltContainer<SingleCoinFragment> {
            Navigation.setViewNavController(requireView(), navController)

        }
        onView(withId(R.id.singleCoinFragment)).check(matches(isDisplayed()))
    }

    @Test
    fun clickInsertIntoDb_CoinInsertedToDb() {
        val testViewModel = CoinsViewModel(FakeAndroidCoinRepositoryImpl())
        launchFragmentInHiltContainer<SingleCoinFragment>(fragmentFactory = fragmentFactory) {
            //viewModel = testViewModel
        }

       // on click on add button -> see if the coin is added
    }


    /*
    * Verify if we press the Back Button the navigation will popBackStack
     */
    @Test
    fun pressBackButton_popBackStack() {
        val navController = mock(NavController::class.java)
        launchFragmentInHiltContainer<SingleCoinFragment> {
            Navigation.setViewNavController(requireView(), navController)
            //args = SingleCoinFragmentArgs("btc")
        }

        pressBack()

        verify(navController).popBackStack()
    }


}