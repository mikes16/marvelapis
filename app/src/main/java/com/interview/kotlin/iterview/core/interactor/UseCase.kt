package com.interview.kotlin.iterview.core.interactor

import com.interview.kotlin.iterview.core.exception.Failure
import com.interview.kotlin.iterview.core.functional.Either
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

/**
 * Abstract class for a Use Case (Interactor in terms of Clean Architecture).
 * This abstraction represents an execution unit for different use cases (this means than any use
 * case in the application should implement this contract).
 *
 * By convention each [UseCase] implementation will execute its job in a background thread
 * (kotlin coroutine) and will post the result in the UI thread.
 */
typealias CompletionBlock<T> = (Either<Failure, T>) -> Unit

abstract class UseCase<out Type, in Params> where Type : Any {

    private var parentJob: Job = Job()
    private var backgroundContext: CoroutineContext = Dispatchers.IO
    private var foregroundContext: CoroutineContext = Dispatchers.Main

    abstract suspend fun run(params: Params): Either<Failure, Type>


    operator fun invoke(params: Params, onResult: CompletionBlock<Type>) {
        unsubscribe()
        parentJob = Job()
        CoroutineScope(foregroundContext + parentJob).launch{
            val result = withContext(backgroundContext) {
                run(params)
            }

            onResult(result)
        }
    }

    fun unsubscribe() {
        parentJob.apply {
            cancelChildren()
            cancel()
        }
    }

    class None
}