package sample

object IosWriter {

    fun write(path: String, content: CsvData) {
        val exportPath = "$path/ios"
        content.languages.forEach { lang ->
            if(lang.isEmpty()) return@forEach
            val stringBuilder = StringBuilder()
            stringBuilder.append("//generated strings")
            content.keys.forEachIndexed { keyIndex, key ->
                val value = content.values[lang]!![keyIndex]
                stringBuilder.append("\"$key\" = \"${value.replaceFormats()}\";\n")
            }
            FileReader.writeFile("$exportPath/localized$lang.strings", stringBuilder.toString())
            println("Generated iOS strings for $lang")
        }
    }

    private fun CharSequence.replaceFormats(): CharSequence {
        val result = replace(Regex("[%][s]"), "\\%\\@")
        return result.replace(Regex("[$][s]"), "\\$\\@")
    }

}