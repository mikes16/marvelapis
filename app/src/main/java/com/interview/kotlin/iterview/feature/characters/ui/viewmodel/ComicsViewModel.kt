package com.interview.kotlin.iterview.feature.characters.ui.viewmodel

import android.arch.lifecycle.MutableLiveData
import com.interview.kotlin.interview.model.ComicsView
import com.interview.kotlin.iterview.core.platform.BaseViewModel
import com.interview.kotlin.iterview.feature.characters.domain.Comics
import com.interview.kotlin.iterview.feature.characters.domain.interactor.GetComics
import com.interview.kotlin.iterview.feature.characters.domain.interactor.GetComics.Params
import javax.inject.Inject

class ComicsViewModel
@Inject constructor(
        private val getComics: GetComics
): BaseViewModel(){

    val commicsView = MutableLiveData<ComicsView>()

    /**
     * Returns a @either value either a Failure or @Comic response
     * */
    fun loadComics(characterId: Int, limit: Int) =
            getComics(Params(characterId, limit)){it.either(::handleFailure, ::handleComics)}

    /**
     * Response from Interactor with @Comics
     * @see @Comics
    * */
    private fun handleComics(comics: Comics) {
        this.commicsView.value = ComicsView(
                comics.code,
                comics.status,
                comics.copyright,
                comics.data
        )
    }
}