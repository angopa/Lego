package com.example.legomvvm.di

import javax.inject.Qualifier

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class LegoApi

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class CoroutinesScopeIO