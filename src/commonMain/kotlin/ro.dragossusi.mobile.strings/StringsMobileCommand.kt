package ro.dragossusi.mobile.strings

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.default
import com.github.ajalt.clikt.parameters.arguments.validate
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.enum
import pw.binom.copyTo
import pw.binom.io.file.*

class StringsMobileCommand : CliktCommand() {

    val path: String by argument(help = "File to be converted").validate {
        File(it).isExist
    }
    val destination: String by argument(help = "Destination").default(".")
    val platform: WriteTarget by option(help = "platform").enum<WriteTarget>()
        .default(WriteTarget.ALL)

    val useUTF8:Boolean by option(help = "Choose to use UTF8 reader").flag(default = false)

    override fun run() {
        File(path).read().copyTo(File("copy.csv").write())
        println(File(path).readText())
        val data = CsvData.from(FileReader.readCsv(path,useUTF8))

        data.check()

        if (platform == WriteTarget.ANDROID || platform == WriteTarget.ALL)
            AndroidWriter.write(destination, data)

        if (platform == WriteTarget.IOS || platform == WriteTarget.ALL)
            IosWriter.write(destination, data)
    }

}