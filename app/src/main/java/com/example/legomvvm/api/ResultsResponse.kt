package com.example.legomvvm.api

import com.google.gson.annotations.SerializedName

data class ResultsResponse<T> (
    @SerializedName("count")
    val count: Int,
    @SerializedName("next")
    val next: String,
    @SerializedName("previous")
    val previous: String? = null,
    @SerializedName("results")
    val results: List<T>
)