package com.interview.kotlin.iterview.core.di.module

import com.interview.kotlin.iterview.core.navigation.RouteActivity
import com.interview.kotlin.iterview.feature.characters.ui.activities.CharacterDetailActivity
import com.interview.kotlin.iterview.feature.characters.ui.activities.CharactersActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector
    abstract fun bindRouteActivity (): RouteActivity

    @ContributesAndroidInjector
    abstract fun bindCharacterActivity (): CharactersActivity

    @ContributesAndroidInjector
    abstract fun bindCharacterDetailActivity (): CharacterDetailActivity
}