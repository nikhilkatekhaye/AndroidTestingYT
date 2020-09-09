package com.nikhil.androidtestingyt.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.nikhil.androidtestingyt.MainCoroutineRule
import com.nikhil.androidtestingyt.getOrAwaitValueTest
import com.nikhil.androidtestingyt.other.Constants
import com.nikhil.androidtestingyt.other.Status
import com.nikhil.androidtestingyt.repositories.FakeShoppingRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ShoppingViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get : Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: ShoppingViewModel


    @Before
    fun setUp() {
        viewModel = ShoppingViewModel(FakeShoppingRepository())
    }

    @Test
    fun `insert shopping item with empty feild, return error`() {

        viewModel.insertShoppingItem("name", "", "3.0")
        val value = viewModel.insertShoppingItemStatus.getOrAwaitValueTest()
        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }


    @Test
    fun `insert shopping item with too long name, return error`() {

        val string = buildString {
            for (i in 1..Constants.MAX_NAME_LENGTH + 1)
            {
                append(1)
            }
        }

        viewModel.insertShoppingItem(string, "5", "3.0")

        val value = viewModel.insertShoppingItemStatus.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }


    @Test
    fun `insert shopping item with too long Price, return error`() {

        val string = buildString {
            for (i in 1..Constants.MAX_PRICE_LENGTH + 1)
            {
                append(1)
            }
        }

        viewModel.insertShoppingItem("name", "5", string)

        val value = viewModel.insertShoppingItemStatus.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }



    @Test
    fun `insert shopping item with too high amount, return error`() {


        viewModel.insertShoppingItem("name", "99999999999999999999", "3.0")

        val value = viewModel.insertShoppingItemStatus.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }


    @Test
    fun `insert shopping item with valid input, return Sucess`() {

        viewModel.insertShoppingItem("name", "5", "3.0")

        val value = viewModel.insertShoppingItemStatus.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.SUCCESS)
    }
}