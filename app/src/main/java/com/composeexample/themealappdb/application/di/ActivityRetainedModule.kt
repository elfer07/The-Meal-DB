package com.composeexample.themealappdb.application.di

import com.composeexample.themealappdb.data.local.LocalRepository
import com.composeexample.themealappdb.data.local.OfflineLocalRepository
import com.composeexample.themealappdb.data.remote.MealRepository
import com.composeexample.themealappdb.data.remote.MealRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class ActivityRetainedModule {
    @Binds
    abstract fun dataSource(impl: MealRepositoryImpl): MealRepository

    @Binds
    abstract fun localDataSource(impl: OfflineLocalRepository): LocalRepository
}