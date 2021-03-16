package ro.dragossusi.mobile.strings

fun CharSequence.replaceIosFormats(): CharSequence {
    val result = replace(Regex("[%][s]"), "\\%\\@")
    return result.replace(Regex("[$][s]"), "\\$\\@")
}

fun CharSequence.replaceQuotes(): CharSequence {
    return replace(Regex("\""), """\\"""")
}