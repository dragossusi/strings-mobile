package ro.dragossusi.mobile.strings

data class TranslationsData(
    val lang: String,
    val items: Map<String, String>
) {

    fun countNonEmptyItems(): Int = items.count {
        it.value.isNotEmpty()
    }

}