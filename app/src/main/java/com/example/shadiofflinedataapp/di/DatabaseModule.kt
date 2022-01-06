package com.example.shadiofflinedataapp.di

import com.example.shadiofflinedataapp.data.local.dao.MatchedAcceptRejectUserDao
import com.example.shadiofflinedataapp.data.local.dao.RandomUserDao
import com.example.shadiofflinedataapp.data.local.database.RandomUserDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideRandomUserDao(db: RandomUserDatabase): RandomUserDao =
        db.randomUserDao()

    @Provides
    @Singleton
    fun provideMatchedAcceptRejectUserDao(db: RandomUserDatabase): MatchedAcceptRejectUserDao =
        db.matchAcceptRejectDao()

}