import com.brian.wordle.api.ApiClient
import com.brian.wordle.usecase.GetRandomWordUseCase
import com.brian.wordle.usecase.PlayGameUseCase
import com.brian.wordle.usecase.RandomWordResults
import com.brian.wordle.userinput.SelectDifficulty
import java.io.File
import com.brian.wordle.data.Constants

suspend fun main() {
    val apiClient = ApiClient()
    val getRandomWordsUseCase = GetRandomWordUseCase(apiClient)
    val playGame = PlayGameUseCase()
    suspend fun getRandomWordFromApi(): String? {
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

    /**
     * Create master word list from the file. The file is the official Scrabble TWL(tournament word list)
     */
    val masterWordList = mutableListOf<String>()
    File("src/main/kotlin/com/brian/wordle/data/words").readLines().forEach {
        (masterWordList.add(it))
    }
    /**
     * I feel like there are ways to combine some of these statements to make the code more concise
     */
    val fiveLetterWords = masterWordList.filter { it.length == 5 } // filter 5-letter words
    val wordListAsCharArrays = mutableListOf<CharArray>()
    fiveLetterWords.forEach { wordListAsCharArrays.add(it.toCharArray()) } // adds the 5-letter words to a new list as char arrays

    when (SelectDifficulty().getDifficulty()) {
        Constants.EASY -> {
            /**
             * This difficulty will find words that have no repeating letters, this could be sxpaneded upon to find more
             * common words
             */
            val answer = wordListAsCharArrays.filter { it.distinct().count() == 5 }.random().joinToString("")
            playGame.wordleGame(answer, masterWordList)
        }
        Constants.MEDIUM -> {
            /**
             * This difficulty will use all 5-letter words in the list, so words can have repeating letters
             */
            val answer = fiveLetterWords.random()
            playGame.wordleGame(answer, masterWordList)
        }
        Constants.HARD -> {
            /**
             * This difficulty will pull a random word from the words API
             */
            val answer = getRandomWordFromApi() //Gets random word from API
            if (answer == null) {
                println("Failed to retrieve word from API")
            } else {
                playGame.wordleGame(answer, masterWordList)
            }
            /**
             *There is a bug here, the api can pick words that aren't in the word list file, so even though the correct
             * word may be chosen, the input will fail because it is not found in the word list that the function checks
             * against
             *
             */
        }
    }
}