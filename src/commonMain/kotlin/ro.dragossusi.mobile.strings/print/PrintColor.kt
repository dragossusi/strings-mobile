package ro.dragossusi.mobile.strings.print

enum class PrintColor(
    val color: String
) {
    RESET("\u001B[0m"),

    YELLOW("\u001B[33m"),

    RED("\u001B[31m");
}