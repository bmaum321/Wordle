package com.brian.wordle.userinput

/**
 * Should check if used input is a valid word in dictionary, Can do this with either a dictionary API or with
 * using some local word list file and checking the input against the list
 */
fun getGuess(wordList: List<String>): String {
    println("Enter your guess: ")
    var userGuess = readln()
    while (userGuess.length != 5 || userGuess !in wordList) {
        println("Guess is either not 5 characters or is not a real word, please enter a new guess")
        userGuess = readln()
    }
    return userGuess
}

/**
 * This version doesn't check if input is in the word list because the API can contain words that aren't in the list
 */
fun getGuessForHardDifficulty(): String {
    println("Enter your guess: ")
    var userGuess = readln()
    while (userGuess.length != 5) {
        println("Guess is not 5 characters, please enter a new guess")
        userGuess = readln()
    }
    return userGuess
}
