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
        var word: String? = readWord() ?: return null
        while (word != null) {
            words += word
            word = readWord()
        }
        return words
    }

    /**
     * @return word and new position
     */
    fun Reader.readWord(): String? {
        val stringBuilder = StringBuilder()
        val firstChar = read() ?: return null
        when (firstChar) {
            WORD_SEPARATOR -> {
                return stringBuilder.toString()
            }
            QUOTE_SEPARATOR -> {
                var char = read()
                while (char != QUOTE_SEPARATOR) {
                    stringBuilder.append(char)
                    //avoid out of bounds
                    char = readCharFromLine() ?: break
                }
                return stringBuilder.toString()
            }
            ENDL_START -> {
                val char = read()
                if (char != ENDL_END) throw Exception()
                return null
            }
            ENDL_END -> {
                return null
            }
            else -> {
                stringBuilder.append(firstChar)
                var char = read()
                //read until separators
                while (char != WORD_SEPARATOR && char != QUOTE_SEPARATOR && char != ENDL_START) {
                    stringBuilder.append(char)
                    //avoid out of bounds
                    char = readCharFromLine() ?: break
                }
                return stringBuilder.toString()
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