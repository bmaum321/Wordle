package com.brian.wordle.usecase

import com.brian.wordle.api.ApiClient
import com.brian.wordle.models.ErrorResponse
import com.brian.wordle.models.Random

class GetRandomWordUseCase(private val apiClient: ApiClient) {
    suspend fun getRandomWord(): RandomWordResults {
        return when (val response = apiClient.getRandomWord()) {
            is ApiClient.ApiResponse.Success -> RandomWordResults.Success(response.data)
            is ApiClient.ApiResponse.Failure -> RandomWordResults.Failure(response.message) // TODO() placeholder
        }
    }
}

sealed class RandomWordResults {
    class Success(val response: Random) : RandomWordResults()
    class Failure(val message: ErrorResponse) : RandomWordResults()
}