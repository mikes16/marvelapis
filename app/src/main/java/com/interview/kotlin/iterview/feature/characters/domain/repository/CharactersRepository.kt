package com.interview.kotlin.iterview.feature.characters.domain.repository

import android.arch.lifecycle.Transformations
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import com.interview.kotlin.iterview.core.exception.Failure
import com.interview.kotlin.iterview.core.exception.Failure.NetworkConnection
import com.interview.kotlin.iterview.core.functional.Either
import com.interview.kotlin.iterview.core.functional.Either.Left
import com.interview.kotlin.iterview.core.functional.Either.Right
import com.interview.kotlin.iterview.core.platform.Listing
import com.interview.kotlin.iterview.core.platform.NetworkHandler
import com.interview.kotlin.iterview.feature.characters.data.net.CharactersService
import com.interview.kotlin.iterview.feature.characters.ui.model.CharactersView
import java.util.concurrent.Executor
import javax.inject.Inject

interface CharactersRepository{
    fun getCharactersList(offset: Int, limit: Int): Either<Failure, Listing<CharactersView.DataBean.ResultsBean>>

    class Network
    @Inject constructor(private val networkHandler: NetworkHandler,
                        private val service: CharactersService,
                        private val networkExecutor: Executor): CharactersRepository{

        override fun getCharactersList(offset: Int, limit: Int): Either<Failure, Listing<CharactersView.DataBean.ResultsBean>> {

            val sourceFactory = CharacterDataSourceFactory(service,offset, networkExecutor)
            val pagedListConfig = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(limit)
                    .setPageSize(limit)
                    .build()
            val livePagedList = LivePagedListBuilder(sourceFactory, pagedListConfig)
                    .setFetchExecutor(networkExecutor)
                    .build()

            val refreshState = Transformations.switchMap(sourceFactory.sourceLiveData) {
                it.initialLoad
            }
            return when (networkHandler.isConnected) {
                true -> returnEither(
                    Listing(
                            pagedList = livePagedList,
                            networkState = Transformations.switchMap(sourceFactory.sourceLiveData) {
                                it.networkState
                            },
                            retry = {
                                sourceFactory.sourceLiveData.value?.retryAllFailed()
                            },
                            refresh = {
                                sourceFactory.sourceLiveData.value?.invalidate()
                            },
                            refreshState = refreshState
                    )
                ) {
                    it
                }
                false, null -> Left(NetworkConnection())
            }
        }

        private fun <T, R> returnEither(entry: Listing<T>,transform: (Listing<T>) -> R): Either<Failure, R> {
            return Right(transform(entry))
        }
    }
}