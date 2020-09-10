package com.nikhil.androidtestingyt.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.nikhil.androidtestingyt.getOrAwaitValue
import com.nikhil.androidtestingyt.launchFragmentInHiltContainer
import com.nikhil.androidtestingyt.ui.ShoppingFragment
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject
import javax.inject.Named

@ExperimentalCoroutinesApi
@SmallTest
@HiltAndroidTest
class ShoppingDaoTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named("test_db")
    lateinit var database: ShoppingItemDatabase
    private lateinit var dao : ShoppingDao

    @Before
    fun setup(){
        hiltRule.inject()
        dao = database.shoppingDao()
    }

    @After
    fun tearDown(){
        database.close()
    }

    @Test
    fun insertShoppingItem() = runBlockingTest {

        val shoppingItem = ShoppingItem("name", 1,1f,"url" , id = 1)
        dao.insertShoppingItem(shoppingItem)
        val allShoppingItem = dao.observeAllShoppingItems().getOrAwaitValue()
        assertThat(allShoppingItem).contains(shoppingItem)
    }

    @Test
    fun deleteShoppingItem() = runBlockingTest {

        val shoppingItem = ShoppingItem("name", 1,1f,"url" , id = 1)
        dao.insertShoppingItem(shoppingItem)
        dao.deleteShoppingItem(shoppingItem)
        val allShoppingItem = dao.observeAllShoppingItems().getOrAwaitValue()
        assertThat(allShoppingItem).doesNotContain(shoppingItem)
    }

    @Test
    fun observeTotalPriceSum() = runBlockingTest {

        val shoppingItem1 = ShoppingItem("name", 2,10f,"url" , id = 1)
        val shoppingItem2 = ShoppingItem("name", 4,5.5f,"url" , id = 2)
        val shoppingItem3 = ShoppingItem("name", 0,100f,"url" , id = 3)
        dao.insertShoppingItem(shoppingItem1)
        dao.insertShoppingItem(shoppingItem2)
        dao.insertShoppingItem(shoppingItem3)

        var totalPriceSum =  dao.observeTotalPrice().getOrAwaitValue()
        assertThat(totalPriceSum).isEqualTo(2 * 10f + 4 * 5.5f)
    }
}