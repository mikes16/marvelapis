package com.interview.kotlin.iterview.core.di.module

import com.interview.kotlin.iterview.feature.characters.ui.fragments.CharactersDetailFragment
import com.interview.kotlin.iterview.feature.characters.ui.fragments.CharactersFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuilder {

    @ContributesAndroidInjector
    internal abstract fun contributeCharactersFragment(): CharactersFragment

    @ContributesAndroidInjector
    internal abstract fun contributeCharactersDetailFragment(): CharactersDetailFragment
}