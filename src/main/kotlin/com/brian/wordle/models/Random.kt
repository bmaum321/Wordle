package com.brian.wordle.models
import kotlinx.serialization.*

@Serializable
data class Random(val word: String) {
}