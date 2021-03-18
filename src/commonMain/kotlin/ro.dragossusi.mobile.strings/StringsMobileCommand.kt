package ro.dragossusi.mobile.strings

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.default
import com.github.ajalt.clikt.parameters.arguments.validate
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.enum
import pw.binom.io.file.File
import pw.binom.io.file.isExist

class StringsMobileCommand : CliktCommand() {

    val path: String by argument(help = "File to be converted").validate {
        File(it).isExist
    }
    val destination: String by argument(help = "Destination").default(".")
    val platform: WriteTarget by option(help = "platform").enum<WriteTarget>()
        .default(WriteTarget.ALL)

    override fun run() {
        val data = CsvData.from(FileReader.readCsv(path))

        data.check()

        if (platform == WriteTarget.ANDROID || platform == WriteTarget.ALL)
            AndroidWriter.write(destination, data)

        if (platform == WriteTarget.IOS || platform == WriteTarget.ALL)
            IosWriter.write(destination, data)
    }

}