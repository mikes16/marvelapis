package com.interview.kotlin.iterview.core.di.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.interview.kotlin.iterview.feature.characters.ui.viewmodel.CharactersViewModel
import com.interview.kotlin.iterview.feature.characters.ui.viewmodel.ComicsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(CharactersViewModel::class)
    abstract fun bindsCharactersViewModel(charactersViewModel: CharactersViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ComicsViewModel::class)
    abstract fun bindsComicsViewModel(charactersViewModel: ComicsViewModel): ViewModel
}