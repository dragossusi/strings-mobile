package ro.dragossusi.mobile.strings

import ro.dragossusi.mobile.strings.print.warning

class CsvData constructor(
    val languages: List<String>,
    val keys: List<String>,
    val values: Map<String, TranslationsData>,
    val size: Int
) {

    init {
        val size = keys.size
        values.forEach {
            if (it.value.items.size != size) {
                throw Exception("Got less values than keys for ${it.key}")
            }
        }
    }

    fun check() {
        val max = values.values.maxOfOrNull {
            it.countNonEmptyItems()
        } ?: return
        values.forEach { (key, value) ->
            val nonEmptyValuesCount = value.countNonEmptyItems()
            if (nonEmptyValuesCount != max) {
                warning(
                    "$key has less values",
                )
            }
        }
    }

    companion object {
        fun from(csv: List<List<String>>): CsvData {
            val header = csv.first()
            val languages = mutableListOf<String>()
            //map of langauge:list of translations
            val translates: MutableMap<String, MutableMap<String, String>> = mutableMapOf()
            for (i in 1 until header.size) {
                languages.add(header[i])
                translates[header[i]] = mutableMapOf()
            }
            val keys = mutableListOf<String>()
            //every language
            for (i in 1 until csv.size) {
                val line = csv[i]
                println(
                    "reading line: ${
                        line.joinToString(
                            ";",
                            prefix = "[",
                            postfix = "]"
                        )
                    }"
                )
                //read key
                val translationKey = line.first()
                keys += translationKey
                //read values
                for (lnaguageIndex in 0 until languages.size) {
                    //every column
                    val list = translates[languages[lnaguageIndex]]!!
                    try {
                        //word
                        list[translationKey] = line[lnaguageIndex + 1]
                    } catch (e: Exception) {
                        println(e)
                    }
                }
            }
            return CsvData(
                languages,
                keys,
                translates.mapValues {
                    TranslationsData(it.key, it.value)
                },
                keys.size
            )
        }
    }

    override fun toString(): String {
        return "CsvData(languages=$languages, keys=$keys, values=$values, size=$size)"
    }

}