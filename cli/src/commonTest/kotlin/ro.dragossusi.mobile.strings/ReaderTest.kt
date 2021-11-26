package ro.dragossusi.mobile.strings

import okio.Buffer
import okio.BufferedSource
import ro.dragossusi.mobile.strings.reader.SourceReader.readCsv
import ro.dragossusi.mobile.strings.reader.SourceReader.readWord
import ro.dragossusi.mobile.strings.reader.SourceReader.readWords
import kotlin.test.Test
import kotlin.test.assertEquals

class ReaderTest {

    @Test
    fun testReadWords() {
        val reader: BufferedSource = """
            asd,"asd","asd""asd""fg",bcd,
            """.trimIndent().toReader()
        val words = reader.readWords()
        assertEquals(
            listOf(
                "asd",
                "asd",
                """asd"asd"fg""",
                "bcd"
            ),
            words
        )
    }

    @Test
    fun testReadWord() {
        val reader = """asd""".trimIndent().toReader()
        assertEquals(
            "asd",
            reader.readWord()?.word
        )
    }

    @Test
    fun testReadCsv() {
        val reader = """
                key_05,Some text,,"Some , Text"
                key_06,,,
                key_07,Another text,,Another text.
                key_08,,,
                key_09,,,
            """.trimIndent().toReader()
        val result = reader.readCsv()
        println(
            result.joinToString(separator = "\n") {
                it.joinToString()
            }
        )
    }

    @Test
    fun testEmptyWords() {
        val reader = """
            a,,,
        """.trimIndent().toReader()
        val result = reader.readCsv()
        assertEquals(
            1,
            result.size
        )
        val row = result.first()
        assertEquals(
            4,
            row.size,
            row.joinToString { it }
        )
    }

    private fun String.toReader(): Buffer {
        val buffer = Buffer()
        buffer.writeUtf8(this)
        return buffer
    }

}