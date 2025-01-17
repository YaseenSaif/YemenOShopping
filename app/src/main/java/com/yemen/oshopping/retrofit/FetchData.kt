package com.yemen.oshopping.retrofit

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yemen.oshopping.api.CategoryResponse
import com.yemen.oshopping.api.ProductResponse
import com.yemen.oshopping.model.Category
import com.yemen.oshopping.model.ProductItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


const val TAG = "fetchProduct"

class FetchData {



    fun fetchProduct(): LiveData<List<ProductItem>> {
        return fetchProductMetaData(RetrofitClient().oshoppingApi.fetchProduct())
    }



    fun fetchProductByCategory(category_id: Int): LiveData<List<ProductItem>> {
        return fetchProductMetaData(RetrofitClient().oshoppingApi.fetchProductByCategory(category_id))
    }
    fun searchProduct(query: String): LiveData<List<ProductItem>> {
        return fetchProductMetaData(RetrofitClient().oshoppingApi.searchProduct(query))
    }

    fun fetchProductMetaData(productRequest: Call<ProductResponse>): LiveData<List<ProductItem>> {
        val responseLiveData: MutableLiveData<List<ProductItem>> = MutableLiveData()

        productRequest.enqueue(object : Callback<ProductResponse> {

            override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
                Log.d(TAG, "Failed to fetch Product", t)
            }

            override fun onResponse(
                call: Call<ProductResponse>,
                response: Response<ProductResponse>
            ) {
                Log.d(TAG, "Response received successfully")
                val productResponse: ProductResponse? = response.body()
                val productItems: List<ProductItem> = productResponse?.productItem
                    ?: mutableListOf()
                responseLiveData.value = productItems
            }
        })

        return responseLiveData
    }


    fun fetchCategory(): LiveData<List<Category>> {
        val responseLiveData: MutableLiveData<List<Category>> = MutableLiveData()
        var categoryRequest: Call<CategoryResponse> = RetrofitClient().oshoppingApi.fetchCategory()
        categoryRequest.enqueue(object : Callback<CategoryResponse> {

            override fun onFailure(call: Call<CategoryResponse>, t: Throwable) {
                Log.d(TAG, "Failed to fetch Product", t)
            }

            override fun onResponse(
                call: Call<CategoryResponse>,
                response: Response<CategoryResponse>
            ) {
                Log.d(TAG, "Response received successfully")
                val categoryResponse: CategoryResponse? = response.body()
                val categoryItems: List<Category> = categoryResponse?.categoryItem
                    ?: mutableListOf()
                responseLiveData.value = categoryItems
            }
        })

        return responseLiveData
    }
}