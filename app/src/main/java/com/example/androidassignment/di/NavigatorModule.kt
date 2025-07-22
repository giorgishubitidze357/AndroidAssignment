package com.example.androidassignment.di

import com.example.androidassignment.presentation.main.navigator.MainNavigator
import com.example.androidassignment.presentation.main.navigator.MainNavigatorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class NavigatorModule {

    @Binds
    @Singleton
    abstract fun bindMainNavigator(impl: MainNavigatorImpl): MainNavigator
}