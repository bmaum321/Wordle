package com.brian.wordle.usecase

import com.brian.wordle.data.Constants
import com.brian.wordle.userinput.SelectDifficulty
import com.brian.wordle.userinput.getGuess
import com.brian.wordle.userinput.getGuessForHardDifficulty

class PlayGameUseCase {
    /**
     * You have to guess the Wordle in six goes or less.
    Every word you enter must be in the word list....
    A correct letter turns green. ( in the console it is put in the answer builder)
    A correct letter in the wrong place turns yellow ( in the console, it is added to a list that is shown)
    An incorrect letter turns gray. (in the console it is added to a list that is shown)
    Letters can be used more than once(depending on difficulty)
     */
    fun wordleGame(answer: String?, masterWordList: List<String>, difficulty: Int) {
        val answerCharArray = answer?.toCharArray() // turns answer into a character array
        var userGuessCharArray: CharArray = if (difficulty != Constants.HARD) {
            getGuess(masterWordList).toCharArray() // Gets initial guess from user input as char array
        } else {
            getGuessForHardDifficulty().toCharArray() // this function doesn't check if input is in list
        }

        var guessCount = 0
        val guessedCharsInAnswer = mutableListOf<Char>()
        val guessedCharsNotInAnswer = mutableListOf<Char>()
        val answerBuilder = mutableListOf(
            '_',
            '_',
            '_',
            '_',
            '_'
        ) // this is what will be outputted to console as the user makes guesses
        /**
         * PLay the game while the input does not equal the answer and with 6 total guesses
         */
        while (!userGuessCharArray.contentEquals(answerCharArray) && guessCount < 5) {
            /**
             * Compares the guess to the answer char by char, if the chars are equal, put that char in the answer builder
             */
            answerCharArray?.forEach {
                if (it == userGuessCharArray[answer.indexOf(it)]) {
                    answerBuilder[answer.indexOf(it)] = it
                }
            }
            /**
             * This needs to be separate from other if statement because there can be duplicate letters in answer which
             * can be confusing in the console. If the guess has a character that is in the answer, but in the wrong position,
             * add it to the list and display
             */
            answerCharArray?.forEach {
                if (it in userGuessCharArray) {
                    guessedCharsInAnswer.add(it)
                }
            }
            /**
             * Print the answer builder
             */
            println(
                answerBuilder.toString().replace(",", "").replace("[", "").replace("]", "").trim()
            )

            userGuessCharArray.forEach { guessedCharsNotInAnswer.add(it) } // creates list of all guessed characters
            /**
             * Creating a list of characters that are not in answer by removing the ones that are from the total list
             * of guessed chars
             */
            guessedCharsInAnswer.forEach {
                if (it in guessedCharsNotInAnswer) {
                    guessedCharsNotInAnswer.remove(it)
                }
            }
            println("Characters not in the answer: ${guessedCharsNotInAnswer.toSet()}")
            println("Characters in the answer: ${guessedCharsInAnswer.toSet()}") //to set gets rid of duplicates in list
            guessCount++
            // gets new answer from user
            userGuessCharArray = if (difficulty != Constants.HARD) {
                getGuess(masterWordList).toCharArray() // Gets guess from user input as char array
            } else {
                getGuessForHardDifficulty().toCharArray() // this function doesn't check if input is in list
            }
        }

        if (userGuessCharArray.contentEquals(answerCharArray)) {
            println("\nCongrats! You win! Answer was: $answer")
        } else {
            println("\nSorry, please try again")
            print("Answer was: $answer")
        }
    }
}