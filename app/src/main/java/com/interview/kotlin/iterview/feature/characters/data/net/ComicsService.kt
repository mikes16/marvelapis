package com.interview.kotlin.iterview.feature.characters.data.net

import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ComicsService
@Inject constructor(retrofit: Retrofit) : ComicsApi{

    private val comicsApi by lazy { retrofit.create(ComicsApi::class.java) }

    override fun getComicsByCharacter(characterId: Int, limit: Int) = comicsApi.getComicsByCharacter(characterId, limit)
    override fun getComicDetail(comicId: Int) = comicsApi.getComicDetail(comicId)
}