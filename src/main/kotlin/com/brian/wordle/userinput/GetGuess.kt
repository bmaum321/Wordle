package com.brian.wordle.userinput

/**
 * Should check if used input is a valid word in dictionary, Can do this with either a dictionary API or with
 * using some local word list file and checking the input against the list
 */
fun getGuess(): String {
    println("Enter your guess: ")
    var userGuess = readln()
    while (userGuess.length != 5) {
        println("Guess must be 5 characters, enter new guess")
        userGuess = readln()
    }
    return userGuess
}
