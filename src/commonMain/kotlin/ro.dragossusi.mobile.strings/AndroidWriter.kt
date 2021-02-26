package sample

object AndroidWriter {

    fun write(path: String, content: CsvData) {
        val exportPath = "$path/android"
        content.languages.forEach { lang ->
            if(lang.isEmpty()) return@forEach
            val stringBuilder = StringBuilder()
            stringBuilder.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>")
            stringBuilder.append("<!--generated strings-->")
            stringBuilder.append("<resources>")
            content.keys.forEachIndexed { keyIndex, key ->
                val value = content.values[lang]!!.getOrNull(keyIndex) ?: ""
                val contentValue = transform(value)
                stringBuilder.append("    <string name=\"$key\">$contentValue</string>\n")
            }
            stringBuilder.append("</resources>")
            FileReader.writeFile("$exportPath/values-${lang.toLowerCase()}/strings.xml", stringBuilder.toString())
            println("Generated Android strings for $lang")
        }
    }

    private fun transform(content: String): String {
        return content.replace("'", "\\'")
    }

}