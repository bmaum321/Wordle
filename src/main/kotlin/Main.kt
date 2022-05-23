import com.brian.wordle.api.ApiClient
import com.brian.wordle.usecase.GetRandomWordUseCase
import com.brian.wordle.usecase.RandomWordResults
import com.brian.wordle.userinput.getGuess
import java.io.File



suspend fun main() {
    /*
    val apiClient = ApiClient()
    val getRandomWordsUseCase = GetRandomWordUseCase(apiClient)


    suspend fun getRandomWord(): String? {
        return when (val random = getRandomWordsUseCase.getRandomWord()) {
            is RandomWordResults.Success -> {
                //println(random.response.word)
                val answer = random.response.word
                answer
            }
            is RandomWordResults.Failure -> {
                println(random.message.message)
                null
            }
        }
    }

     */

    val wordList = mutableListOf<String>()
    File("src/main/kotlin/com/brian/wordle/data/words").readLines().forEach {
        (wordList.add(it))
    }
    val answer = wordList.filter { it.length == 5 }.random()
   // val answer = getRandomWord() //Gets random word from API
    val answerCharArray = answer?.toCharArray() // turns it into a character array
    var userGuessCharArray = getGuess(wordList).toCharArray() // Gets initial guess from user input
    var guessCount = 0
    val guessedCharsInAnswer = mutableListOf<Char>()
    val guessedCharsNotInAnswer = mutableListOf<Char>()
    val answerBuilder = mutableListOf('_', '_', '_', '_', '_')


    while (! userGuessCharArray.contentEquals(answerCharArray) && guessCount < 5) {
        answerCharArray?.forEach {
            if (it == userGuessCharArray[answer.indexOf(it)]) {
                answerBuilder[answer.indexOf(it)] = it
            }
        }
        /**
         * This needs to be separate from other if statement because there can be duplicate letters in answer which
         * can be confusing in the console
         */
        answerCharArray?.forEach {
            if (it in userGuessCharArray) {
                guessedCharsInAnswer.add(it)
            }
        }
        println(
                answerBuilder.toString().replace(",", "").replace("[", "").replace("]", "").trim()
        )

        userGuessCharArray.forEach { guessedCharsNotInAnswer.add(it) } // creates list of all guessed characters
        /**
         * should subtract chars in answer from this list
         */
        guessedCharsInAnswer.forEach {
            if( it in guessedCharsNotInAnswer) {
                guessedCharsNotInAnswer.remove(it)
            }
        }
        println("Characters not in the answer: ${guessedCharsNotInAnswer.toSet()}")
        println("Characters in the answer: ${guessedCharsInAnswer.toSet()}") //to set gets rid of duplicates in list
        guessCount ++
        userGuessCharArray = getGuess(wordList).toCharArray() // gets new answer from user
    }

    if (userGuessCharArray.contentEquals(answerCharArray)) {
        println("\nCongrats! You win! Answer was: $answer")
    } else {
        println("\nSorry, please try again")
        print("Answer was: $answer")
    }

}