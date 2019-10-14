package com.example.legomvvm.worker

import android.content.Context
import android.util.JsonReader
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.legomvvm.util.DATA_FILENAME
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import timber.log.Timber

class SeedDatabaseWorker (
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result = coroutineScope {
        withContext(Dispatchers.IO) {

            try {
                applicationContext.assets.open(DATA_FILENAME).use {inputStream ->
                    JsonReader(inputStream.reader()).use { jsonReader ->
                        Result.success()
                    }
                }
            } catch (e: Exception) {
                Timber.e(e, "Error sending database")
                Result.failure()
            }
        }
    }
}