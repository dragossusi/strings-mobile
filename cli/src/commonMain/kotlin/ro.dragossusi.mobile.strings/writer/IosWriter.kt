package ro.dragossusi.mobile.strings.writer

import okio.Path
import ro.dragossusi.mobile.strings.reader.FileReader
import ro.dragossusi.mobile.strings.replaceIosFormats
import ro.dragossusi.mobile.strings.replaceQuotes

class IosWriter(
    private val fileReader: FileReader
) : Writer(fileReader) {

    override fun onFileWritten(lang: String) {
        super.onFileWritten(lang)
        println("Generated iOS strings for $lang")
    }

    override fun createExportPath(path: Path, lang: String): Path {
        val exportPath = path.div("ios")
        return exportPath.div("${lang.lowercase()}.strings")
    }

    override fun writeHeader(stringBuilder: StringBuilder) {
        stringBuilder.append("//generated strings\n")
    }

    override fun writeFooter(stringBuilder: StringBuilder) {
        //no footer
    }

    override fun writeEntry(stringBuilder: StringBuilder, key: String, value: String) {
        stringBuilder.append("\"$key\" = \"${value.replaceQuotes().replaceIosFormats()}\";\n")
    }

}