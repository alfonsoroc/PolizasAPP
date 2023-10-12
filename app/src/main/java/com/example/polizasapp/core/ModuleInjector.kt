package com.example.polizasapp.core

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ModuleInjector {

    @Provides
    fun provideContext(application: Application):Context{
        return application.applicationContext
    }

    @Singleton
    @Provides
    fun providePrefs(context: Context):Prefs{
        return Prefs(context)
    }
}