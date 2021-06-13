package ro.dragossusi.mobile.strings

object IosWriter : Writer() {

    override fun onFileWritten(lang: String) {
        super.onFileWritten(lang)
        println("Generated iOS strings for $lang")
    }

    override fun createExportPath(path: String, lang: String): String {
        val exportPath = "$path/ios"
        return "$exportPath/${lang.lowercase()}.strings"
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