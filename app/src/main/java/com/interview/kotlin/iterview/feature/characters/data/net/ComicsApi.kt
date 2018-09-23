package com.interview.kotlin.iterview.feature.characters.data.net

import com.interview.kotlin.iterview.feature.characters.data.entity.ComicsEntity
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ComicsApi {

    companion object {
        private const val COMIC_BY_CHARACTER = "characters/{characterId}/comics"
        private const val COMIC_DETAIL = "comics/{comicId}"
    }

    /**
     * Fetches ComicsView of a Character
     */
    //https://gateway.marvel.com/v1/public/characters/1010338/comics?ts=1&apikey=fb9cf622de091ac20051e62a51c81149&hash=b68e7aeef8e1843eb3b2958aa737c743
    @GET(COMIC_BY_CHARACTER)
    fun getComicsByCharacter(
            @Path("characterId") characterId: Int,
            @Query("limit") limit: Int): Call<ComicsEntity>

    /**
     * Fetches Comic Detail
     */
    //https://gateway.marvel.com/v1/public/comics/61431?ts=1&apikey=fb9cf622de091ac20051e62a51c81149&hash=b68e7aeef8e1843eb3b2958aa737c743
    @GET(COMIC_DETAIL)
    fun getComicDetail(
            @Path("comicId") comicId: Int): Call<ComicsEntity>
}