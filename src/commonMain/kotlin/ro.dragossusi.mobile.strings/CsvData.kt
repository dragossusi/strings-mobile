package sample

class CsvData(
    val languages: List<String>,
    val keys: List<String>,
    val values: Map<String, List<String>>,
    val size: Int
) {


    companion object {
        fun from(csv: List<List<String>>): CsvData {
            val header = csv.first()
            val languages = mutableListOf<String>()
            //map of langauge:list of translations
            val translates: MutableMap<String, MutableList<String>> = mutableMapOf()
            for (i in 1 until header.size) {
                languages.add(header[i])
                translates[header[i]] = mutableListOf()
            }
            val keys = mutableListOf<String>()
            //every language
            for (i in 1 until csv.size) {
                val line = csv[i]
                println(
                    "reading line: ${line.joinToString(
                        ";",
                        prefix = "[",
                        postfix = "]"
                    )}"
                )
                //read key
                keys += line.first()
                //read values
                for (lnaguageIndex in 0 until languages.size) {
                    //every column
                    val list = translates[languages[lnaguageIndex]]!!
                    try {
                        list.add(
                            //word
                            line[lnaguageIndex + 1]
                        )
                    } catch (e: Exception) {
                        println(e)
                    }
                }
            }
            return CsvData(
                languages,
                keys,
                translates,
                keys.size
            )
        }
    }

    override fun toString(): String {
        return "CsvData(languages=$languages, keys=$keys, values=$values, size=$size)"
    }

}