package ro.dragossusi.mobile.strings

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.default
import com.github.ajalt.clikt.parameters.arguments.validate
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.enum
import okio.FileSystem
import okio.Path.Companion.toPath
import ro.dragossusi.mobile.strings.reader.FileReader
import ro.dragossusi.mobile.strings.writer.AndroidWriter
import ro.dragossusi.mobile.strings.writer.IosWriter

class StringsMobileCommand(
    private val fileSystem: FileSystem,
) : CliktCommand() {

    private val fileReader = FileReader(fileSystem)

    val path: String by argument(help = "File to be converted").validate {
        fileSystem.exists(it.toPath())
    }
    val destination: String by argument(help = "Destination").default(".")
    val platform: WriteTarget by option(help = "platform").enum<WriteTarget>()
        .default(WriteTarget.ALL)

    override fun run() {
        val filePath = path.toPath()
        fileSystem.copy(
            filePath,
            "copy.csv".toPath()
        )
        println(
            fileSystem.read(filePath) {
                readUtf8()
            }
        )
        val data = CsvData.from(fileReader.readCsv(filePath))

        data.check()

        val destinationPath = destination.toPath()

        if (platform == WriteTarget.ANDROID || platform == WriteTarget.ALL) {
            AndroidWriter(fileReader).write(destinationPath, data)
        }

        if (platform == WriteTarget.IOS || platform == WriteTarget.ALL) {
            IosWriter(fileReader).write(destinationPath, data)
        }
    }

}