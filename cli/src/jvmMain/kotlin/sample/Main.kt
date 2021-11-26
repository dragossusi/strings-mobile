package sample

import okio.FileSystem
import ro.dragossusi.mobile.strings.StringsMobileCommand

fun main(args: Array<String>) {
    StringsMobileCommand(FileSystem.SYSTEM).main(args)
}