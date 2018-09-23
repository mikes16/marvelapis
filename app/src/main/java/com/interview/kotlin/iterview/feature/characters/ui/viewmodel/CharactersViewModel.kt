package com.interview.kotlin.iterview.feature.characters.ui.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations.map
import android.arch.lifecycle.Transformations.switchMap
import android.arch.paging.PagedList
import com.interview.kotlin.iterview.feature.characters.ui.model.CharactersView
import com.interview.kotlin.iterview.core.platform.BaseViewModel
import com.interview.kotlin.iterview.core.platform.Listing
import com.interview.kotlin.iterview.core.platform.NetworkState
import com.interview.kotlin.iterview.feature.characters.domain.interactor.GetCharacters
import com.interview.kotlin.iterview.feature.characters.domain.interactor.GetCharacters.Params
import javax.inject.Inject

class CharactersViewModel
@Inject constructor(
        private val getCharacters: GetCharacters
) : BaseViewModel() {

    val characterList = MutableLiveData<CharactersView>()
    lateinit var repoResult: LiveData<Listing<CharactersView.DataBean.ResultsBean>>
    lateinit var posts: LiveData<PagedList<CharactersView.DataBean.ResultsBean>>
    lateinit var networkState: LiveData<NetworkState>
    lateinit var refreshState: LiveData<NetworkState>

    fun loadCharacters(offset: Int, limit: Int) =
            getCharacters(Params(offset, limit)){it.either(::handleFailure, ::handleCharacterListing)}

    private fun handleCharacterListing(characterListing: Listing<CharactersView.DataBean.ResultsBean>) {
        repoResult = map(characterList){
            characterListing
        }
        posts = switchMap(repoResult) { it.pagedList }
        networkState = switchMap(repoResult) { it.networkState }
        refreshState = switchMap(repoResult) { it.refreshState }
        characterList.postValue(CharactersView())
    }

    /**
     * Retries the request if something goes wrong
     * */
    fun retry() {
        val listing = repoResult.value
        listing?.retry?.invoke()
    }

    /**
     * Regresh the list with new values with pull to refresh
     */
    fun refresh() {
        repoResult.value?.refresh?.invoke()
    }

}