package com.interview.kotlin.iterview.feature.characters.ui.fragments

import android.arch.lifecycle.Observer
import android.arch.paging.PagedList
import android.os.Bundle
import android.support.annotation.StringRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.interview.kotlin.iterview.R
import com.interview.kotlin.iterview.core.exception.Failure
import com.interview.kotlin.iterview.core.extensions.failure
import com.interview.kotlin.iterview.core.extensions.observe
import com.interview.kotlin.iterview.core.extensions.viewModel
import com.interview.kotlin.iterview.core.navigation.Navigator
import com.interview.kotlin.iterview.core.platform.BaseFragment
import com.interview.kotlin.iterview.core.platform.GlideApp
import com.interview.kotlin.iterview.core.platform.NetworkState
import com.interview.kotlin.iterview.feature.characters.ui.adapters.CharactersAdapter
import com.interview.kotlin.iterview.feature.characters.ui.exception.CharactersException
import com.interview.kotlin.iterview.feature.characters.ui.model.CharactersView
import com.interview.kotlin.iterview.feature.characters.ui.viewmodel.CharactersViewModel
import kotlinx.android.synthetic.main.fragment_characters.*
import javax.inject.Inject

class CharactersFragment : BaseFragment(){

    @Inject
    lateinit var navigator: Navigator
    private lateinit var charactersViewModel: CharactersViewModel
    private var isInit = false

    override fun layoutId(): Int = R.layout.fragment_characters

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        charactersViewModel = viewModel(viewModelFactory){
            observe(characterList, ::loadFinish)
            failure(failure, ::handleFailure)
        }

        charactersViewModel.loadCharacters(0,10)

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    private fun loadFinish(characters: CharactersView?) {
        initAdapter()
        initSwipeToRefresh()
        isInit = true
    }

    private fun handleFailure(failure: Failure?) {
        when (failure) {
            is Failure.NetworkConnection -> renderFailure(R.string.failure_network)
            is Failure.ServerError -> renderFailure(R.string.server_error)
            is CharactersException.DataNotAvailable -> renderFailure(R.string.empty_response)
        }
    }

    private fun renderFailure(@StringRes message: Int) {
        notify(message)
    }

    private fun initAdapter() {

        val glide = GlideApp.with(this)
        val adapter = CharactersAdapter(glide)
        list.adapter = adapter
        charactersViewModel.posts.observe(this, Observer<PagedList<CharactersView.DataBean.ResultsBean>> {
            adapter.submitList(it)
        })
        charactersViewModel.networkState.observe(this, Observer {
            adapter.setNetworkState(it)
        })

        adapter.clickListener = { character ->
            navigator.showCharacterDetails(activity!!, character) }
    }

    private fun initSwipeToRefresh() {
        charactersViewModel.refreshState.observe(this, Observer {
            swipe_refresh.isRefreshing = it == NetworkState.LOADING
        })
        swipe_refresh.setOnRefreshListener {
            charactersViewModel.refresh()
        }
    }

    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        if(isConnected){
            if(isInit){
//                charactersViewModel.retry()
            }
        }else{
            renderFailure(R.string.failure_network)
        }
    }
}