package com.interview.kotlin.iterview.feature.characters.domain.interactor

import com.interview.kotlin.iterview.core.interactor.UseCase
import com.interview.kotlin.iterview.core.platform.Listing
import javax.inject.Inject
import com.interview.kotlin.iterview.feature.characters.domain.interactor.GetCharacters.Params
import com.interview.kotlin.iterview.feature.characters.domain.repository.CharactersRepository
import com.interview.kotlin.iterview.feature.characters.ui.model.CharactersView

class GetCharacters
@Inject constructor(private val charactersRepository: CharactersRepository) : UseCase<Listing<CharactersView.DataBean.ResultsBean>, Params>() {

    override suspend fun run(params: Params) = charactersRepository.getCharactersList(params.offset, params.limit)

    data class Params(val offset: Int, val limit: Int)
}