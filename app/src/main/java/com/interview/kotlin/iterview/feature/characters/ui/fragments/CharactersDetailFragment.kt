package com.interview.kotlin.iterview.feature.characters.ui.fragments

import android.content.Context
import android.os.Bundle
import android.support.annotation.StringRes
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.interview.kotlin.interview.model.ComicsView
import com.interview.kotlin.iterview.R
import com.interview.kotlin.iterview.core.exception.Failure
import com.interview.kotlin.iterview.core.extensions.determineThumbnailUrl
import com.interview.kotlin.iterview.core.extensions.failure
import com.interview.kotlin.iterview.core.extensions.observe
import com.interview.kotlin.iterview.core.extensions.viewModel
import com.interview.kotlin.iterview.core.platform.BaseFragment
import com.interview.kotlin.iterview.core.platform.GlideApp
import com.interview.kotlin.iterview.feature.characters.ui.activities.CharacterDetailActivity
import com.interview.kotlin.iterview.feature.characters.ui.adapters.CharactersAdapter
import com.interview.kotlin.iterview.feature.characters.ui.adapters.ComicsAdapter
import com.interview.kotlin.iterview.feature.characters.ui.exception.CharactersException
import com.interview.kotlin.iterview.feature.characters.ui.model.CharactersView
import com.interview.kotlin.iterview.feature.characters.ui.viewmodel.ComicsViewModel
import kotlinx.android.synthetic.main.fragment_characters_detail.*

class CharactersDetailFragment : BaseFragment(){

    companion object {
        private const val PARAM_CHARACTER = "param_character"

        fun forCharacter(character: CharactersView.DataBean.ResultsBean?): CharactersDetailFragment {
            val charactersDetailsFragment = CharactersDetailFragment()
            val arguments = Bundle()
            arguments.putParcelable(PARAM_CHARACTER, character)
            charactersDetailsFragment.arguments = arguments

            return charactersDetailsFragment
        }
    }

    private lateinit var comicsViewModel: ComicsViewModel
    private lateinit var charactersView: CharactersView.DataBean.ResultsBean

    override fun layoutId(): Int = R.layout.fragment_characters_detail

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        charactersView = (arguments!![PARAM_CHARACTER] as CharactersView.DataBean.ResultsBean)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        comicsViewModel = viewModel(viewModelFactory){
            observe(commicsView, ::loadFinish)
            failure(failure, ::handleFailure)
        }

        comicsViewModel.loadComics(charactersView.id,20)

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val image = view.determineThumbnailUrl(charactersView.thumbnail?.path) + "." +
            charactersView.thumbnail?.extension

        Glide.with(appContext)
                .load(image)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(character_image)

        character_description.text = if(!charactersView.description.isNullOrEmpty())
            charactersView.description else resources.getString(R.string.empty_description)

        (activity as CharacterDetailActivity).supportActionBar?.title = charactersView.name
    }

    fun loadFinish(comics: ComicsView?){
        Log.d("CharactersDetail", comics.toString())

        val glide = GlideApp.with(this)
        val adapter = ComicsAdapter(glide, comics?.data?.results!!)

        comic_recycler.adapter = adapter
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

    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        if(isConnected){
            comicsViewModel.loadComics(charactersView.id,20)
        }else{
            renderFailure(R.string.failure_network)
        }
    }
}