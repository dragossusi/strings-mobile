package ro.dragossusi.mobile.strings.print

fun error(message: Any) {
    print(PrintColor.RED.color)
    print(message)
    println(PrintColor.RESET.color)
}

fun warning(message: Any) {
    print(PrintColor.YELLOW.color)
    print(message)
    println(PrintColor.RESET.color)
}