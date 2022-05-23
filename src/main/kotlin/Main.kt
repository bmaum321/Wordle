import com.brian.wordle.api.ApiClient
import com.brian.wordle.usecase.GetRandomUseCase
import com.brian.wordle.usecase.GetWordsUseCase
import com.brian.wordle.usecase.RandomResults
import com.brian.wordle.usecase.Results

suspend fun main(args: Array<String>) {
    val apiClient = ApiClient()
    val getRandomWordsUseCase = GetRandomUseCase(apiClient)

    suspend fun getRandom(): String? {
        val random = getRandomWordsUseCase.getRandomWord()
        return when (random) {
            is RandomResults.Success -> {
             //   println(random.response.word)
                val answer = random.response.word
                answer
            }
            is RandomResults.Failure -> {
                println(random.message.message)
                null
            }
        }
    }

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

    val answer = getRandom()?.toCharArray()
    var userGuessCharArray = getGuess().toCharArray()
    var guessCount = 0
    val charsInAnswer = mutableListOf<Char>()
   // val answerBuilder = mutableListOf<Char>()
    var answerBuilder = mutableMapOf<Int, Char>(0 to '_', 1 to '_', 2 to '_', 3 to '_', 4 to '_')

    /**
     * Also need to track if anly letters in guess are in the answer
     */

    while (!userGuessCharArray.contentEquals(answer) && guessCount < 5) {
/*
        answer?.forEach {
            if (it == userGuessCharArray[answer.indexOf(it)]) {
                print("$it ")
            } else {
                print("_ ")
            }
        }

 */

        answer?.forEach {
            if (it == userGuessCharArray[answer.indexOf(it)]) {
                answerBuilder.put(answer.indexOf(it), it )
            }
        }
        answer?.forEach {
            if (it in userGuessCharArray) {
                charsInAnswer.add(it)
            }
        }
        println(
            answerBuilder.values.toString().replace(",", "").
            replace("[","").replace("]", "").trim()
        )
        println(" Characters in answer: ${charsInAnswer.toSet()}") //to set gets rid of duplicates in list
        guessCount++
        userGuessCharArray = getGuess().toCharArray()
    }

    if (userGuessCharArray.contentEquals(answer)) {
        println(" \n Congrats! You win! Answer was: ${answer?.forEach { print(it) }}")
    } else {
        println(" \n Sorry, please try again")
        print("Answer was: ${answer?.forEach { print(it) }}")

    }


}