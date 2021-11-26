package ro.dragossusi.mobile.strings.writer

import okio.ForwardingFileSystem
import okio.Path
import ro.dragossusi.mobile.strings.reader.FileReader

class AndroidWriter(fileReader: FileReader) : Writer(fileReader) {

    override fun onFileWritten(lang: String) {
        super.onFileWritten(lang)
        println("Generated Android strings for $lang")
    }

    override fun createExportPath(path: Path, lang: String): Path {
        val exportPath = path.div("android")
        return exportPath.div("values-${lang.lowercase()}")
            .div("strings.xml")
    }

    override fun writeHeader(stringBuilder: StringBuilder) {
        stringBuilder.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n")
        stringBuilder.append("<!--generated strings-->\n")
        stringBuilder.append("<resources>\n")
    }

    override fun writeEntry(stringBuilder: StringBuilder, key: String, value: String) {
        val contentValue = transform(value)
        stringBuilder.append("    <string name=\"$key\">$contentValue</string>\n")
    }

    override fun writeFooter(stringBuilder: StringBuilder) {
        stringBuilder.append("</resources>")
    }

    private fun transform(content: String): String {
        return content.replace("'", "\\'")
            .replace("&", "&amp;")
    }

}