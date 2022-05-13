package com.example.weather.helper.util

import kotlinx.coroutines.flow.*

inline fun <ResultType, RequestType> networkBoundResource(
    crossinline query: () -> Flow<ResultType>,
    crossinline fetch: suspend () -> RequestType,
    crossinline save: suspend (RequestType) -> Unit,
    crossinline need: (ResultType) -> Boolean = { true }
) = flow {
    val data = query().first()

    val flow =  if(need(data)){
        emit(Resource.Loading(data))

        try {
            save(fetch())
            query().map { Resource.Success(it) }
        } catch (throwable: Throwable){
            query().map { Resource.Error(throwable, it) }
        }

    } else{
        query().map { Resource.Success(it) }
    }

    emitAll(flow)

}