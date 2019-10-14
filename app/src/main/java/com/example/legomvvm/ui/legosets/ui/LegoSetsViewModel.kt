package com.example.legomvvm.ui.legosets.ui

import androidx.lifecycle.ViewModel
import com.example.legomvvm.di.CoroutinesScopeIO
import com.example.legomvvm.ui.legosets.data.LegoSetRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import javax.inject.Inject

/**
 * The ViewModel for [LegoSetsFragment].
 */
class LegoSetsViewModel @Inject constructor(private val repository: LegoSetRepository,
                                            @CoroutinesScopeIO private val ioCoroutineScope: CoroutineScope
): ViewModel() {
    var connectivityAvailable: Boolean = false
    var themeId: Int? = null

    val legoSets by lazy {
        repository.observePagedSets(connectivityAvailable, themeId, ioCoroutineScope)
    }

    /**
     * Cancel all coroutines when the ViewModel is cleared.
     */
    override fun onCleared() {
        super.onCleared()
        ioCoroutineScope.cancel()
    }
}