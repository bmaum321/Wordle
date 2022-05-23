package com.brian.wordle.usecase

import com.brian.wordle.api.ApiClient
import com.brian.wordle.models.ErrorResponse
import com.brian.wordle.models.Random
import com.brian.wordle.models.Words

class GetRandomUseCase(private val apiClient: ApiClient) {
    suspend fun getRandomWord(): RandomResults {
        val response = apiClient.getRandomWord()
        when (response) {
            is ApiClient.ApiResponse.Success -> return RandomResults.Success(response.data)
            is ApiClient.ApiResponse.Failure -> return RandomResults.Failure(response.message) // TODO() placeholder
        }
    }
}

sealed class RandomResults {
    class Success(val response: Random) : RandomResults()
    class Failure(val message: ErrorResponse) : RandomResults()
}