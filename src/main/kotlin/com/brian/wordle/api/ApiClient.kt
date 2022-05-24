package com.brian.wordle.api

import com.brian.wordle.models.ErrorResponse
import com.brian.wordle.models.Random
import com.brian.wordle.models.Words
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

class ApiClient {
    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
    }

    suspend fun getWord(): ApiResponse<Words> {
        val response = this.client.get(RequestParameters.BASEURL) {
            parameter("letters", 5)
            // parameter("limit", 100)
            parameter("page", 1)
            parameter("random", true)
            header("X-RapidAPI-Key", ApiConstants.API_KEY)
            header("X-RapidAPI-Host", "wordsapiv1.p.rapidapi.com")
            // header("Accept", "application/json")
        }
        println("Response is $response")

        return if (response.status.value in 200..299) {
            ApiResponse.Success(response.body())
        } else {
            ApiResponse.Failure(response.body())
        }

    }

    /**
     *
     * These parameters can be modified to change the difficulty of the game, maybe consider adding a level chooser
     */
    suspend fun getRandomWord(): ApiResponse<Random> {
        val response = this.client.get(RequestParameters.BASEURL) {
            parameter("letters", 5)
            //  parameter("limit", 100)
            //  parameter("page", 1)
            // parameter("letterPattern", "^a.{4}$") // regex starts with a
            parameter("frequency", 7)
            /**
             * I think a low diversity score means the words are more common
             */
            // parameter("diversity", 0)
            parameter("random", true)
            // parameter("frequencymin", 8.03)
            parameter("syllablesMin", 2) // should pick words with at least 2 vowels,
            header("X-RapidAPI-Key", ApiConstants.API_KEY)
            header("X-RapidAPI-Host", "wordsapiv1.p.rapidapi.com")
            // header("Accept", "application/json")
        }
        // println("Response is $response")

        return if (response.status.value in 200..299) {
            ApiResponse.Success(response.body())
        } else {
            ApiResponse.Failure(response.body())
        }
    }

    sealed class ApiResponse<T> {
        class Success<T>(val data: T) : ApiResponse<T>()
        class Failure<T>(val message: ErrorResponse) : ApiResponse<T>()
    }
}