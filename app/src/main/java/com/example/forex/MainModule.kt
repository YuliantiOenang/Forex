package com.example.forex

import android.app.Activity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
object MainModule {
    @Provides
    fun provideActivity(activity: Activity): MainActivity {
        return activity as MainActivity
    }

    @Provides
    fun provideMainActivity2(activity: Activity): MainActivity2 {
        return activity as MainActivity2
    }
}