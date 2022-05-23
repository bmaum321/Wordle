package com.brian.wordle.userinput

class SelectDifficulty {
    fun getDifficulty(): Int {
        println("Select difficulty: ")
        println("1: Easy\n2: Medium\n3: Hard")
        var difficultyChoice = readln().toInt()
        while (difficultyChoice !in 1..3) {
            println("Please select a valid difficulty")
            difficultyChoice = readln().toInt()
        }
        return difficultyChoice
    }
}