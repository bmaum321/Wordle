package com.brian.wordle.usecase

import com.brian.wordle.api.ApiClient
import com.brian.wordle.api.ApiClient.ApiResponse
import com.brian.wordle.models.ErrorResponse
import com.brian.wordle.models.Words

class GetWordsUseCase(private val apiClient: ApiClient) {

    suspend fun getWords(): Results {
        val response = apiClient.getWord()
        return when (response) {
            is ApiResponse.Success -> Results.Success(response.data)
            is ApiResponse.Failure -> Results.Failure(response.message) // TODO() placeholder
        }
    }
}


sealed class Results {
    class Success(val response: Words) : Results()
    class Failure(val message: ErrorResponse) : Results()
}