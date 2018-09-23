package com.interview.kotlin.iterview.core.di.module

import android.app.Application
import android.content.Context
import com.interview.kotlin.iterview.InterviewApplication
import dagger.Binds
import dagger.Module

@Module
abstract class AppModule{

        @Binds
        internal abstract fun bindContext(application: InterviewApplication): Context
}