package com.nikhil.androidtestingyt.repositories

import androidx.lifecycle.LiveData
import com.nikhil.androidtestingyt.data.local.ShoppingItem
import com.nikhil.androidtestingyt.data.remote.responses.ImageResponse
import com.nikhil.androidtestingyt.other.Resource
import retrofit2.Response

interface ShoppingRepository {

    suspend fun insertShoppingItem(shoppingItem: ShoppingItem)

    suspend fun deleteShoppingItem(shoppingItem: ShoppingItem)

    fun observeAllShoppingItems() : LiveData<List<ShoppingItem>>

    fun observeTotalPrice() : LiveData<Float>

    suspend fun searchForImage(imageQuery : String) : Resource<ImageResponse>

}