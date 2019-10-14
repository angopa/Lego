package com.example.legomvvm.di

import android.app.Application
import com.example.legomvvm.BuildConfig
import com.example.legomvvm.api.AuthInterceptor
import com.example.legomvvm.api.LegoService
import com.example.legomvvm.data.AppDatabase
import com.example.legomvvm.ui.legosets.data.LegoSetRemoteDataSource
import com.example.legomvvm.ui.legotheme.data.LegoThemeRemoteDataSource
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class, CoreDataModule::class])
class AppModule {

    @Provides
    @Singleton
    fun provideLegoService(@LegoApi okHttpClient: OkHttpClient, converterFactory: GsonConverterFactory) =
        provideService(okHttpClient, converterFactory, LegoService::class.java)

    @Provides
    @Singleton
    fun provideLegoSetRemoteDataSource(legoService: LegoService) =
        LegoSetRemoteDataSource(legoService)

    @Provides
    @Singleton
    fun provideLegoThemeRemoteDataSource(legoService: LegoService) =
        LegoThemeRemoteDataSource(legoService)

    @Provides
    @LegoApi
    fun providesPrivateOkHttpClient(
        upstreamClient: OkHttpClient
    ): OkHttpClient {
        return upstreamClient.newBuilder()
            .addInterceptor(AuthInterceptor(BuildConfig.API_DEVELOPER_TOKEN)).build()
    }

    @Provides
    @Singleton
    fun providesDb(app: Application) = AppDatabase.getInstance(app)

    @Provides
    @Singleton
    fun provideLegoThemeDao(db: AppDatabase) = db.legoThemeDao()

    @Provides
    @Singleton
    fun provideLegoSetDao(db: AppDatabase) = db.legoSetDao()

    @CoroutinesScopeIO
    @Provides
    fun provideCoroutineScopeIO() = CoroutineScope(Dispatchers.IO)

    private fun createRetrofit(
        okHttpClient: OkHttpClient,
        converterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(LegoService.ENDPOINT)
            .client(okHttpClient)
            .addConverterFactory(converterFactory)
            .build()
    }

    private fun <T> provideService(
        okHttpClient: OkHttpClient,
        converterFactory: GsonConverterFactory,
        clazz: Class<T>
    ): T {
        return createRetrofit(okHttpClient, converterFactory).create(clazz)
    }
}