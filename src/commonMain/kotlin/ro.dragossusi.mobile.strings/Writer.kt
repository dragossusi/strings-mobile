package ro.dragossusi.mobile.strings

/**
 * strings-mobile
 *
 * @author dragos
 * @since 12/03/2021
 *
 * Copyright (C) 2020  Rachieru Dragos-Mihai
 *
 * strings-mobile is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 *
 * strings-mobile is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with strings-mobile.  If not, see [License](http://www.gnu.org/licenses/) .
 *
 */
abstract class Writer {

    open fun write(path: String, content: CsvData) {
        content.languages.forEach { lang ->
            if (lang.isEmpty()) return@forEach
            val stringBuilder = StringBuilder()
            writeHeader(stringBuilder)
            content.keys.forEach { key ->
                val value = content.values[lang]!!.items.get(key)
                if (!value.isNullOrEmpty()) {
                    writeEntry(stringBuilder, key, value)
                }
            }
            writeFooter(stringBuilder)
            FileReader.writeFile(createExportPath(path, lang), stringBuilder.toString())
            onFileWritten(lang)
        }
    }

    open fun onFileWritten(lang: String) {

    }

    abstract fun createExportPath(path: String, lang: String): String

    abstract fun writeHeader(stringBuilder: StringBuilder)
    abstract fun writeFooter(stringBuilder: StringBuilder)

    abstract fun writeEntry(stringBuilder: StringBuilder, key: String, value: String)


}