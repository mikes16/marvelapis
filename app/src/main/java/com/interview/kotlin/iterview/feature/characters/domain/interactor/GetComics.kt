package com.interview.kotlin.iterview.feature.characters.domain.interactor

import com.interview.kotlin.iterview.core.interactor.UseCase
import com.interview.kotlin.iterview.feature.characters.domain.Comics
import com.interview.kotlin.iterview.feature.characters.domain.interactor.GetComics.Params
import com.interview.kotlin.iterview.feature.characters.domain.repository.ComicsRepository
import javax.inject.Inject

class GetComics
@Inject constructor(private val comicsRepository: ComicsRepository) : UseCase<Comics, Params>() {

    override suspend fun run(params: Params) = comicsRepository
            .getComicsByCharacter(params.characterId, params.limit)

    data class Params(val characterId: Int, val limit: Int)
}