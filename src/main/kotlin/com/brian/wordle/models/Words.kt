package com.brian.wordle.models
import kotlinx.serialization.*

@Serializable
data class Words(val results: Total){
}

@Serializable
data class Total(val total: List<Data>)

@Serializable
data class Data(val number: Int)