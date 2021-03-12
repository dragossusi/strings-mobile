package ro.dragossusi.mobile.strings

object AndroidWriter : Writer() {

    override fun onFileWritten(lang: String) {
        super.onFileWritten(lang)
        println("Generated Android strings for $lang")
    }

    override fun createExportPath(path: String, lang: String): String {
        val exportPath = "$path/android"
        return "$exportPath/values-${lang.toLowerCase()}/strings.xml"
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
    }

}