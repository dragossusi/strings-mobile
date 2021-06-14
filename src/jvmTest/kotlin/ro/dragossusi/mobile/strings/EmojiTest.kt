package ro.dragossusi.mobile.strings

import pw.binom.io.bufferedReader
import pw.binom.io.file.File
import pw.binom.io.file.read
import pw.binom.io.use
import pw.binom.io.utf8Reader
import kotlin.test.Test

class EmojiTest {

    @Test
    fun testEmojiUtf8() {

        val file = File(this::class.java.classLoader.getResource("emoji.txt")!!.file)

        val builder = StringBuilder()
        file.read().utf8Reader().use {
            while (true) {
                builder.append(it.read() ?: break)
            }
        }

        println(builder.toString())

    }

    @Test
    fun testEmoji() {

        val file = File(this::class.java.classLoader.getResource("emoji.txt")!!.file)

        val builder = StringBuilder()
        file.read().bufferedReader().use {
            while (true) {
                builder.append(it.read() ?: break)
            }
        }

        println(builder.toString())

    }

}