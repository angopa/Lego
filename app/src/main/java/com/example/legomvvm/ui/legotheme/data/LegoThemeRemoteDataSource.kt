package com.example.legomvvm.ui.legotheme.data

import com.example.legomvvm.api.BaseDataSource
import com.example.legomvvm.api.LegoService
import com.example.legomvvm.testing.OpenForTesting
import javax.inject.Inject

/**
 * Works with the Lego API to get data.
 */
@OpenForTesting
class LegoThemeRemoteDataSource @Inject constructor(private val service: LegoService) : BaseDataSource() {
    suspend fun fetchData() = getResult {service.getThemes(1, 100, "-id")}
}