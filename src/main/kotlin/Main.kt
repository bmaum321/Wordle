import com.brian.wordle.api.ApiClient
import com.brian.wordle.usecase.GetRandomWordUseCase
import com.brian.wordle.usecase.RandomWordResults
import com.brian.wordle.userinput.getGuess

suspend fun main() {
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

    val answer = getRandomWord() //Gets random word from API
    val answerCharArray = answer?.toCharArray() // turns it into a character array
    var userGuessCharArray = getGuess().toCharArray() // Gets guess from user input
    var guessCount = 0
    val guessedCharsInAnswer = mutableListOf<Char>()
    val totalGuessedChars = mutableListOf<Char>()
    val answerBuilder = mutableMapOf(0 to '_', 1 to '_', 2 to '_', 3 to '_', 4 to '_')

    while (!userGuessCharArray.contentEquals(answerCharArray) && guessCount < 5) {

        answerCharArray?.forEach {
            if (it == userGuessCharArray[answer.indexOf(it)]) {
                answerBuilder.put(answer.indexOf(it), it)
                /**
                 * There is a bug here, Ex. if the word was borty, and the first guess was horte, and next one busty,
                 * it will give you the answer even though you have not guessed it correctly
                 */
            }
        }
        answerCharArray?.forEach {
            if (it in userGuessCharArray) {
                guessedCharsInAnswer.add(it)
            }
        }
        println(
            answerBuilder.values.toString().replace(",", "").replace("[", "").replace("]", "").trim()
        )

        userGuessCharArray.forEach { totalGuessedChars.add(it) } // creates list of all guessed characters
        /**s
         * should subtract chars in answer from this list
         */
        println("List of characters in guesses: ${totalGuessedChars.toSet()}")
        println("Characters in answer: ${guessedCharsInAnswer.toSet()}") //to set gets rid of duplicates in list
        guessCount++
        userGuessCharArray = getGuess().toCharArray() // gets new answer from user
    }

    if (userGuessCharArray.contentEquals(answerCharArray)) {
        println("\nCongrats! You win! Answer was: $answer")
    } else {
        println("\nSorry, please try again")
        print("Answer was: $answer")

    }


}