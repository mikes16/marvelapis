package com.interview.kotlin.iterview.feature.characters.domain.repository

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.DataSource
import com.interview.kotlin.iterview.feature.characters.data.net.CharactersService
import com.interview.kotlin.iterview.feature.characters.data.CharactersDataSource
import com.interview.kotlin.iterview.feature.characters.ui.model.CharactersView
import java.util.concurrent.Executor

/**
 * A simple data source factory which also provides a way to observe the last created data source.
 * This allows us to channel its network request status etc back to the UI. See the Listing creation
 * in the Repository class.
 */
class CharacterDataSourceFactory(
        private val service: CharactersService,
        private val offset: Int,
        private val retryExecutor: Executor) : DataSource.Factory<Int, CharactersView.DataBean.ResultsBean>() {
    val sourceLiveData = MutableLiveData<CharactersDataSource>()
    override fun create(): DataSource<Int, CharactersView.DataBean.ResultsBean>? {
        val source = CharactersDataSource(service, offset, retryExecutor)
        sourceLiveData.postValue(source)
        return source
    }
}