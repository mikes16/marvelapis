package com.interview.kotlin.iterview.feature.characters.domain.repository

import com.interview.kotlin.iterview.core.exception.Failure
import com.interview.kotlin.iterview.core.exception.Failure.NetworkConnection
import com.interview.kotlin.iterview.core.exception.Failure.ServerError
import com.interview.kotlin.iterview.core.functional.Either
import com.interview.kotlin.iterview.core.functional.Either.Left
import com.interview.kotlin.iterview.core.functional.Either.Right
import com.interview.kotlin.iterview.core.platform.NetworkHandler
import com.interview.kotlin.iterview.feature.characters.data.entity.ComicsEntity
import com.interview.kotlin.iterview.feature.characters.data.net.ComicsService
import com.interview.kotlin.iterview.feature.characters.domain.Comics
import retrofit2.Call
import javax.inject.Inject

interface ComicsRepository{
    fun getComicsByCharacter(characterId: Int, limit: Int): Either<Failure, Comics>

    class Network
    @Inject constructor(private val networkHandler: NetworkHandler,
                        private val service: ComicsService): ComicsRepository{

        override fun getComicsByCharacter(characterId: Int, limit: Int): Either<Failure, Comics> {
            return when (networkHandler.isConnected) {
                true -> request(service.getComicsByCharacter(characterId, limit), { it.toComic() } , ComicsEntity())
                false, null -> Left(NetworkConnection())
            }
        }

        private fun <T, R> request(call: Call<T>, transform: (T) -> R, default: T): Either<Failure, R> {
            return try {
                val response = call.execute()
                when (response.isSuccessful) {
                    true -> Right(transform((response.body() ?: default)))
                    false -> Left(ServerError())
                }
            } catch (exception: Throwable) {
                Left(Failure.ServerError())
            }
        }

    }
}