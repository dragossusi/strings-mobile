package ro.dragossusi.mobile.strings

import kotlin.test.Test
import kotlin.test.assertEquals

class WriterTest {

    @Test
    fun testReplaceQuotes() {
        val text = """asd123 "asd"  asd"""

        assertEquals(
            """asd123 \"asd\"  asd""",
            text.replaceQuotes()
        )
    }

}