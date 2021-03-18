package ro.dragossusi.mobile.strings.print

import kotlin.test.Test

class PrintUtilsTest {

    @Test
    fun warning() {
        warning("this is a warning message")
    }

    @Test
    fun error() {
        error("this is an error message")
    }

}