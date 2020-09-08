package com.nikhil.androidtestingyt.repositories

import androidx.lifecycle.LiveData
import com.nikhil.androidtestingyt.data.local.ShoppingDao
import com.nikhil.androidtestingyt.data.local.ShoppingItem
import com.nikhil.androidtestingyt.data.remote.PixabayAPI
import com.nikhil.androidtestingyt.data.remote.responses.ImageResponse
import com.nikhil.androidtestingyt.other.Resource
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

class DefaultShoppingRepository @Inject constructor(
    private val shoppingDao: ShoppingDao,
    private val pixabayAPI: PixabayAPI
) : ShoppingRepository {

    override suspend fun insertShoppingItem(shoppingItem: ShoppingItem) {
        shoppingDao.insertShoppingItem(shoppingItem)
    }

    override suspend fun deleteShoppingItem(shoppingItem: ShoppingItem) {
        shoppingDao.deleteShoppingItem(shoppingItem)
    }

    override fun observeAllShoppingItems(): LiveData<List<ShoppingItem>> {
        return shoppingDao.observeAllShoppingItems()
    }

    override fun observeTotalPrice(): LiveData<Float> {
        return shoppingDao.observeTotalPrice()
    }

    override suspend fun searchForImage(imageQuery: String): Resource<ImageResponse> {
        return try {
            val response = pixabayAPI.searchForImage(imageQuery)
            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("An unknown error", null)
            } else {
                Resource.error("An unknown error occured", null)
            }

        } catch (e: Exception) {
            Resource.error("Could reach to server. check connection", null)
        }
    }
}