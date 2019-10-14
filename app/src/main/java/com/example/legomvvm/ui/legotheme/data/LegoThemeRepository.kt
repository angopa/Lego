package com.example.legomvvm.ui.legotheme.data

import com.example.legomvvm.data.resultLiveData
import com.example.legomvvm.testing.OpenForTesting
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository module for handling data operations.
 */
@Singleton
@OpenForTesting
class LegoThemeRepository @Inject constructor(private val dao: LegoThemeDao,
                                              private val remoteSource: LegoThemeRemoteDataSource) {
    val themes = resultLiveData(
        databaseQuery = { dao.getLegoThemes() },
        networkCall = { remoteSource.fetchData() },
        saveCallResult = { dao.insertAll(it.results) }
    )
}