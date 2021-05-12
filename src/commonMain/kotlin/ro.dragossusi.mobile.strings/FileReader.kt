package ro.dragossusi.mobile.strings

import pw.binom.io.Reader
import pw.binom.io.file.*
import pw.binom.io.use
import pw.binom.io.utf8Reader
import pw.binom.toByteBufferUTF8

object FileReader {

    fun readCsv(path: String): List<List<String>> {
        val file = File(path)
        if (!file.isExist) {
            throw Exception("Inexistent file")
        }
        val reader = file.read().utf8Reader()
        return readCsv(reader)
    }

    fun readCsv(reader: Reader): MutableList<List<String>> {
        val result = mutableListOf<List<String>>()

        try {
            var words = reader.readWords()
            while (words != null) {
                result += words
                words = reader.readWords()
            }
        } catch (e: Exception) {
        }
        return result
    }

    fun writeFile(path: String, content: String): Boolean {
        val file = File(path)
        file.parent?.mkdirs()
        file.write().use {
            it.write(content.toByteBufferUTF8())
            it.flush()
        }
        return true
    }

    fun Reader.readWords(): List<String>? {
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
    fun Reader.readWord(lastChar: Char? = null): ReadResult? {
        val stringBuilder = StringBuilder()
        val firstChar = read() ?: run {
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
                var char = read()
                do {
                    if (char == QUOTE_SEPARATOR) {
                        char = read()
                        if (char == QUOTE_SEPARATOR) {
                            stringBuilder.append('"')
                            char = read()
                        } else {
                            var endLine = false
                            if (char == ENDL_END) {
                                endLine = true
                            }
                            if (char == ENDL_START) {
                                char = read()
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
                val char = read()
                if (char != ENDL_END) throw Exception()
                return ReadResult(stringBuilder.toString(), endLine = true)
            }
            ENDL_END -> {
                return ReadResult(stringBuilder.toString(), endLine = true)
            }
            else -> {
                stringBuilder.append(firstChar)
                var char = read()
                //read until separators
                while (char != WORD_SEPARATOR && char != ENDL_START && char != ENDL_END) {
                    stringBuilder.append(char)
                    //avoid out of bounds
                    char = readCharFromLine() ?: break
                }
                if (char == ENDL_START) {
                    char = read()
                    if (char != ENDL_END) throw Exception()
                }
                return ReadResult(
                    stringBuilder.toString(),
                    endLine = char != WORD_SEPARATOR
                )
            }
        }
    }

    /**
     * avoid out of bounds
     */
    private fun Reader.readCharFromLine(): Char? {
        return try {
            read()
        } catch (e: Exception) {
            null
        }
    }

    const val WORD_SEPARATOR = ','
    const val QUOTE_SEPARATOR = '"'

    const val ENDL_START = '\r'
    const val ENDL_END = '\n'
}