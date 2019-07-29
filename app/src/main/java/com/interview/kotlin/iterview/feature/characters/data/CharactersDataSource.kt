package com.interview.kotlin.iterview.feature.characters.data

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.PageKeyedDataSource
import android.util.Log
import com.interview.kotlin.iterview.core.platform.NetworkState
import com.interview.kotlin.iterview.feature.characters.data.entity.CharactersEntity
import com.interview.kotlin.iterview.feature.characters.data.net.CharactersService
import com.interview.kotlin.iterview.feature.characters.ui.model.CharactersView
import retrofit2.Call
import retrofit2.Response
import java.io.IOException
import java.util.concurrent.Executor

class CharactersDataSource(
        private val service: CharactersService,
        private var offset: Int,
        private val retryExecutor: Executor
) : PageKeyedDataSource<Int, CharactersView.DataBean.ResultsBean>(){

    // keep a function reference for the retry event
    private var retry: (() -> Any)? = null

    /**
     * There is no sync on the state because paging will always call loadInitial first then wait
     * for it to return some success value before calling loadAfter.
     */
    val networkState = MutableLiveData<NetworkState>()

    val initialLoad = MutableLiveData<NetworkState>()

    fun retryAllFailed() {
        val prevRetry = retry
        retry = null
        prevRetry?.let {
            retryExecutor.execute {
                it.invoke()
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>,
                            callback: LoadCallback<Int, CharactersView.DataBean.ResultsBean>) {
        // ignored, since we only ever append to our initial load
    }

    override fun loadAfter(params: LoadParams<Int>,
                           callback: LoadCallback<Int, CharactersView.DataBean.ResultsBean>) {
        networkState.postValue(NetworkState.LOADING)
        service.getCharactersList(params.key, limit = params.requestedLoadSize).enqueue(
                object : retrofit2.Callback<CharactersEntity> {
                    override fun onFailure(call: Call<CharactersEntity>, t: Throwable) {
                        retry = {
                            loadAfter(params, callback)
                        }
                        // publish the error
                        networkState.postValue(NetworkState.error(t.message ?: "unknown err"))
                    }

                    override fun onResponse(
                            call: Call<CharactersEntity>?,
                            response: Response<CharactersEntity>) {

                        if (response.isSuccessful) {
                            val items = response.body()?.data?.results?.map { it } ?: emptyList()

                            retry = null
                            offset += params.requestedLoadSize
                            callback.onResult(items, offset)
                            networkState.postValue(NetworkState.LOADED)
                        }else{
                            retry = {
                                loadAfter(params, callback)
                            }
                            networkState.postValue(
                                    NetworkState.error("error code: ${response.code()}"))
                        }
                    }

                }
        )

    }

    override fun loadInitial(params: LoadInitialParams<Int>,
                             callback: LoadInitialCallback<Int, CharactersView.DataBean.ResultsBean>) {
        val request = service.getCharactersList(
                offset = offset,
                limit = params.requestedLoadSize
        )

        // update network states.
        // we also provide an initial load state to the listeners so that the UI can know when the
        // very first list is loaded.
        networkState.postValue(NetworkState.LOADING)
        initialLoad.postValue(NetworkState.LOADING)

        // triggered by a refresh, we better execute sync
        try {
            val response = request.execute()
            val items = response.body()?.data?.results?.map { it } ?: emptyList()
            retry = null
            networkState.postValue(NetworkState.LOADED)
            initialLoad.postValue(NetworkState.LOADED)
            val previousKey = offset
            offset += params.requestedLoadSize
            callback.onResult(items, previousKey, offset)
        } catch (ioException: IOException) {
            retry = {
                loadInitial(params, callback)
            }
            val error = NetworkState.error(ioException.message ?: "unknown error")
            networkState.postValue(error)
            initialLoad.postValue(error)
        }
    }

}