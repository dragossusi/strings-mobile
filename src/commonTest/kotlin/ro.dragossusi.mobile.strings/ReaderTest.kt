package ro.dragossusi.mobile.strings

import pw.binom.io.StringReader
import ro.dragossusi.mobile.strings.FileReader.readWords
import kotlin.test.Test

class ReaderTest {

    @Test
    fun testReadWord() {
        val reader = StringReader("""asd,"asd","asd""asd""fg",bcd""")
        println(
            reader.readWords()
        )
    }

}