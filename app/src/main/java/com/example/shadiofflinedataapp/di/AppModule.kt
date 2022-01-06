package com.example.shadiofflinedataapp.di

import android.app.Application
import androidx.room.Room
import com.example.shadiofflinedataapp.data.local.database.RandomUserDatabase
import com.example.shadiofflinedataapp.data.remote.api.RandomUserApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(RandomUserApi.BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideRandomUserApi(retrofit: Retrofit): RandomUserApi =
        retrofit.create(RandomUserApi::class.java)


    @Provides
    @Singleton
    fun provideDatabase(app: Application): RandomUserDatabase =
        Room.databaseBuilder(app, RandomUserDatabase::class.java, "random_user_database")
            .build()
}