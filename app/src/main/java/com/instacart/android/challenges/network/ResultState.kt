package com.instacart.android.challenges.network

sealed class ResultState {
    object LOADING : ResultState()
    data class SUCCESS(val response: OrderResponse): ResultState()
    data class ERROR(val error: Exception): ResultState()
}
