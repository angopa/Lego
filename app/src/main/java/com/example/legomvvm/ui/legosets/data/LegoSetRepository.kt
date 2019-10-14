package com.example.legomvvm.ui.legosets.data

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.legomvvm.testing.OpenForTesting
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository module for handling data operations.
 */
@Singleton
@OpenForTesting
class LegoSetRepository @Inject constructor(
    private val dao: LegoSetDao,
    private val legoSetRemoteDataSource: LegoSetRemoteDataSource
) {
    fun observePagedSets(
        connectivityAvailable: Boolean, themeId: Int? = null,
        coroutineScope: CoroutineScope
    ) =
        if (connectivityAvailable) observeRemotePagedSets(themeId, coroutineScope)
        else observeLocalPagedSets(themeId)

    private fun observeLocalPagedSets(themeId: Int?): LiveData<PagedList<LegoSet>> {
        val dataSourceFactory =
            if (themeId == null) dao.getPagedLegoSets()
            else dao.getPagedLegoSetByTheme(themeId)

        return LivePagedListBuilder(
            dataSourceFactory,
            LegoSetPageDataSourceFactory.pagedListConfig()
        ).build()
    }

    private fun observeRemotePagedSets(
        themeId: Int? = null,
        ioCoroutineScope: CoroutineScope
    ): LiveData<PagedList<LegoSet>> {
        val dataSourceFactory =
            LegoSetPageDataSourceFactory(themeId, legoSetRemoteDataSource, dao, ioCoroutineScope)

        return LivePagedListBuilder(
            dataSourceFactory,
            LegoSetPageDataSourceFactory.pagedListConfig()
        ).build()
    }


    companion object {
        const val PAGE_SIZE = 100

        //For singleton instantiation
        @Volatile
        private var instance: LegoSetRepository? = null

        fun getInstance(dao: LegoSetDao, legoSetRemoteDataSource: LegoSetRemoteDataSource) =
            instance ?: synchronized(this) {
                instance ?: LegoSetRepository(dao, legoSetRemoteDataSource).also {
                    instance = it
                }
            }
    }
}
