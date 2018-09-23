package com.interview.kotlin.iterview

import android.app.Activity
import com.interview.kotlin.iterview.core.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.DispatchingAndroidInjector
import javax.inject.Inject

class InterviewApplication:DaggerApplication(){

    private val appComponent: AndroidInjector<InterviewApplication> by lazy {
        DaggerAppComponent
                .builder()
                .create(this)
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> = appComponent

}