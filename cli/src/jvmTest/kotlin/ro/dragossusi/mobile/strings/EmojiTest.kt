package ro.dragossusi.mobile.strings

import okio.FileSystem
import okio.Path.Companion.toOkioPath
import ro.dragossusi.mobile.strings.reader.SourceReader.readChar
import java.io.File
import kotlin.test.Test

class EmojiTest {

    @Test
    fun testEmojiUtf8() {

        val file = File(
            this::class.java.classLoader.getResource("emoji.txt")!!.file
        )

        val builder = StringBuilder()
        FileSystem.SYSTEM.read(file.toOkioPath()) {
            while (!exhausted()) {
                builder.append(readChar() ?: break)
            }
        }

        println(builder.toString())

    }

    @Test
    fun testEmoji() {

        val file = File(this::class.java.classLoader.getResource("emoji.txt")!!.file)

        val builder = StringBuilder()
        FileSystem.SYSTEM.read(file.toOkioPath()) {
            while (!exhausted()) {
                builder.append(readChar() ?: break)
            }
        }

        println(builder.toString())

    }

}