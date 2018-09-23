package com.interview.kotlin.iterview.core.di

import com.interview.kotlin.iterview.InterviewApplication
import com.interview.kotlin.iterview.core.di.module.ActivityBuilder
import com.interview.kotlin.iterview.core.di.module.AppModule
import com.interview.kotlin.iterview.core.di.module.FragmentBuilder
import com.interview.kotlin.iterview.core.di.module.NetworkModule
import com.interview.kotlin.iterview.core.di.viewmodel.ViewModelModule
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    NetworkModule::class,
    ViewModelModule::class,
    AppModule::class,
    ActivityBuilder::class,
    FragmentBuilder::class
])
interface AppComponent : AndroidInjector<InterviewApplication> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<InterviewApplication>()



}