package com.instacart.android.challenges.network

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

interface OrderRepository {
    suspend fun getOrderById(orderId: Long): Flow<ResultState>
}

class OrderRepositoryImpl(
    private val serviceApi: NetworkApi = NetworkService().api
) : OrderRepository {
    override suspend fun getOrderById(orderId: Long): Flow<ResultState> = flow {
        emit(ResultState.LOADING)

        try {
            val response = serviceApi.fetchOrderByIdCoroutine(orderId)
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(ResultState.SUCCESS(it))
                } ?: throw Exception("BODY IS NULL")
            } else {
                throw Exception(response.errorBody()?.string())
            }
        } catch (e: Exception) {
            emit(ResultState.ERROR(e))
        }
    }

}