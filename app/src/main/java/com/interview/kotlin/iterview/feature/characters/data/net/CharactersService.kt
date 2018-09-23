package com.interview.kotlin.iterview.feature.characters.data.net

import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CharactersService
@Inject constructor(retrofit: Retrofit) : CharactersApi {

    private val charactersApi by lazy { retrofit.create(CharactersApi::class.java) }

    override fun getCharactersList(offset: Int, limit: Int) = charactersApi.getCharactersList(offset, limit)
    override fun getCharacterDetail(characterId: Int) = charactersApi.getCharacterDetail(characterId)
}