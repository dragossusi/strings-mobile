package ro.dragossusi.mobile.strings.reader

import okio.BufferedSource
import ro.dragossusi.mobile.strings.ReadResult

object SourceReader {

    fun BufferedSource.readCsv(): MutableList<List<String>> {
        val result = mutableListOf<List<String>>()

        try {
            var words = readWords()
            while (words != null) {
                result += words
                words = readWords()
            }
        } catch (e: Exception) {
        }
        return result
    }

    fun BufferedSource.readWords(): List<String>? {
        val words = mutableListOf<String>()
        var wordResult: ReadResult? = readWord() ?: return null
        while (wordResult != null) {
            words += wordResult.word
            if (wordResult.endLine) wordResult = null
            else wordResult = readWord(wordResult.lastChar)
        }
        return words
    }

    /**
     * @return (word,lastChar) or null for new line
     */
    fun BufferedSource.readWord(lastChar: Char? = null): ReadResult? {
        val stringBuilder = StringBuilder()
        val firstChar = readChar() ?: run {
            //last char was WORD_SEPARATOR, return new word
            if (lastChar == WORD_SEPARATOR) {
                return ReadResult("", endLine = true)
            }
            return null
        }
        when (firstChar) {
            WORD_SEPARATOR -> {
                return ReadResult(
                    stringBuilder.toString(),
                    firstChar,
                )
            }
            QUOTE_SEPARATOR -> {
                //get next char
                var char = readChar()
                do {
                    if (char == QUOTE_SEPARATOR) {
                        char = readChar()
                        if (char == QUOTE_SEPARATOR) {
                            stringBuilder.append('"')
                            char = readChar()
                        } else {
                            var endLine = false
                            if (char == ENDL_END) {
                                endLine = true
                            }
                            if (char == ENDL_START) {
                                char = readChar()
                                if (char != ENDL_END) throw Exception()
                                endLine = true
                            }
                            return ReadResult(stringBuilder.toString(), endLine = endLine)
                        }
                    } else {
                        stringBuilder.append(char)
                        //avoid out of bounds
                        char = readCharFromLine() ?: break
                    }
                } while (true)
                return ReadResult(stringBuilder.toString())
            }
            ENDL_START -> {
                val char = readChar()
                if (char != ENDL_END) throw Exception()
                return ReadResult(stringBuilder.toString(), endLine = true)
            }
            ENDL_END -> {
                return ReadResult(stringBuilder.toString(), endLine = true)
            }
            else -> {
                stringBuilder.append(firstChar)
                var char = readCharFromLine()
                //read until separators
                while (char != WORD_SEPARATOR && char != ENDL_START && char != ENDL_END) {
                    stringBuilder.append(char)
                    //avoid out of bounds
                    char = readCharFromLine() ?: break
                }
                if (char == ENDL_START) {
                    char = readChar()
                    if (char != ENDL_END) throw Exception()
                }
                return ReadResult(
                    stringBuilder.toString(),
                    endLine = char != WORD_SEPARATOR,
                    lastChar = char
                )
            }
        }
    }

    fun BufferedSource.readChar(): Char? {
        if (exhausted()) return null
        return readByte().toChar()
    }

    /**
     * avoid out of bounds
     */
    fun BufferedSource.readCharFromLine(): Char? {
        if (exhausted()) return null
        return try {
            readChar()
        } catch (e: Exception) {
            null
        }
    }

}

private const val WORD_SEPARATOR = ','
private const val QUOTE_SEPARATOR = '"'
private const val ENDL_START = '\r'
private const val ENDL_END = '\n'