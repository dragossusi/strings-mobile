package ro.dragossusi.mobile.strings

import pw.binom.io.StringReader
import pw.binom.io.asReader
import ro.dragossusi.mobile.strings.FileReader.readWord
import ro.dragossusi.mobile.strings.FileReader.readWords
import kotlin.test.Test
import kotlin.test.assertEquals

class ReaderTest {

    @Test
    fun testReadWords() {
        val reader = """
            asd,"asd","asd""asd""fg",bcd,
            """.trimIndent().asReader()
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
        val reader = """asd""".trimIndent().asReader()
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
            """.trimIndent().asReader()
        val result = FileReader.readCsv(reader)
        println(
            result.joinToString(separator = "\n") {
                it.joinToString {
                    it
                }
            }
        )
    }

    @Test
    fun testEmptyWords() {
        val reader = """
            a,,,
        """.trimIndent().asReader()
        val result = FileReader.readCsv(reader)
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

}