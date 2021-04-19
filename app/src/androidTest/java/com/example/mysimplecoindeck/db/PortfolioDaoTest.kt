package com.example.mysimplecoindeck.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.example.mysimplecoindeck.models.dbModels.CoinPortfolioEntity
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlinx.coroutines.ExperimentalCoroutinesApi
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import javax.inject.Inject
import javax.inject.Named

/**
 * JUnit is running on the JVM normally,
 * but instrumented unit test are running on the android emulator
 * -> so we have to specify it with RunWith
 *
 * SmallTest -> Unit Tests (not integrated or UI-Test)
 * Medium -> integrated test
 * large -> ui-test
 */
@ExperimentalCoroutinesApi
@SmallTest
@HiltAndroidTest
class PortfolioDaoTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    /**
     * Add rule for this class -> execute all this code inside the class one after another
     */
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    /**
     * We inject the room db with hilt
     */
    @Inject
    @Named("test_db")
    lateinit var database: PortfolioDatabase
    private lateinit var dao: PortfolioDao

    /**
     * Initialise room db before each test
     */
    @Before
    fun setup() {
        /**
         * Using Room in Memory Database that store data in the RAM for testing purposes
         * -> allow MainThread to run the Queries on the Mainthread
         * -> we want each test with a coroutine to block the Mainthread
         * We inject the db instance with hilt
         */
        hiltRule.inject()
        dao = database.getPortfolioDao()

    }

    /**
     * After each test to prevent flaky tests -> close db
     */
    @After
    fun teardown() {
        database.close()
    }

    private val coinPortfolioEntity = CoinPortfolioEntity(
        "uuid1",
        "TestCoin",
        "www.google.de",
        "42.42",
        "1",
        "100"
    )


    /**
     * runBlockTest will block mainThread
     */
    @Test
    fun insertCoinPortfolioEntity() = runBlockingTest {
        dao.upsert(coinPortfolioEntity)

        val allCoinPortfolioEntities = dao.getAllPortfolioCoins()

        assertThat(allCoinPortfolioEntities).contains(coinPortfolioEntity)
    }

    @Test
    fun deleteCoinPortfolioEntity() = runBlockingTest {
        dao.upsert(coinPortfolioEntity)
        dao.deletePortfolioCoin(coinPortfolioEntity)
        val allCoinPortfolioEntities = dao.getAllPortfolioCoins()

        assertThat(allCoinPortfolioEntities).doesNotContain(coinPortfolioEntity)
    }
}