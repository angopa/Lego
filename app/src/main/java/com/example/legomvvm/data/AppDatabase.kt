package com.example.legomvvm.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.legomvvm.ui.legosets.data.LegoSet
import com.example.legomvvm.ui.legosets.data.LegoSetDao
import com.example.legomvvm.ui.legotheme.data.LegoTheme
import com.example.legomvvm.ui.legotheme.data.LegoThemeDao
import com.example.legomvvm.worker.SeedDatabaseWorker

/**
 * The Room database for this app
 */
@Database(entities = [LegoTheme::class, LegoSet::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun legoThemeDao(): LegoThemeDao

    abstract fun legoSetDao(): LegoSetDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDataBase(context).also { instance = it }
            }
        }

        // Create and pre-populate the database. See this article for more details:
        // https://medium.com/google-developers/7-pro-tips-for-room-fbadea4bfbd1#4785
        private fun buildDataBase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, "legocatalog-db")
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        val request = OneTimeWorkRequestBuilder<SeedDatabaseWorker>().build()
                        WorkManager.getInstance(context).enqueue(request)
                    }
                }).build()
        }
    }
}