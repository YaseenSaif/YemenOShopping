package com.yemen.oshopping.api

import com.google.gson.annotations.SerializedName
import com.yemen.oshopping.model.Category
import com.yemen.oshopping.model.ProductItem

data class CategoryResponse (
    @SerializedName("listOfCategories")
    var categoryItem: List<Category>
)