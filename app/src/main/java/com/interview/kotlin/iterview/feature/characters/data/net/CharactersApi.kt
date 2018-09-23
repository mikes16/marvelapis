package com.interview.kotlin.iterview.feature.characters.data.net

import com.interview.kotlin.iterview.feature.characters.data.entity.CharactersEntity
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

internal interface CharactersApi {

    companion object {
        private const val CHARACTERS = "characters?orderBy=-modified"
        private const val CHARACTER_DETAIL = "characters/{characterId}"
    }

    /**
     * Fetches a list of characters
     */
    //https://gateway.marvel.com/v1/public/characters?ts=1&apikey=fb9cf622de091ac20051e62a51c81149&hash=b68e7aeef8e1843eb3b2958aa737c743
    @GET(CHARACTERS)
    fun getCharactersList(
            @Query("offset") offset: Int,
            @Query("limit") limit: Int): Call<CharactersEntity>

    /**
     * Fetches a Character Detail
     */
    //https://gateway.marvel.com/v1/public/characters/1009148?ts=1&apikey=fb9cf622de091ac20051e62a51c81149&hash=b68e7aeef8e1843eb3b2958aa737c743
    @GET(CHARACTER_DETAIL)
    fun getCharacterDetail(
            @Path("characterId") characterId: Int): Call<CharactersEntity>
}