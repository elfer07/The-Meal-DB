package com.composeexample.themealappdb.application.di

import android.content.Context
import androidx.room.Room
import com.composeexample.themealappdb.application.Constant
import com.composeexample.themealappdb.application.Constant.TABLE
import com.composeexample.themealappdb.data.local.MealDatabase
import com.composeexample.themealappdb.data.remote.WebService
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRetrofitInstance() = Retrofit.Builder()
        .baseUrl(Constant.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .build()

    @Singleton
    @Provides
    fun provideWebService(retrofit: Retrofit) = retrofit.create(WebService::class.java)

    @Singleton
    @Provides
    fun provideRoomInstance(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        MealDatabase::class.java,
        TABLE
    ).build()

    @Singleton
    @Provides
    fun provideMealDao(db: MealDatabase) = db.mealDao()
}