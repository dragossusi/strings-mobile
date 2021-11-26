package ro.dragossusi.mobile.strings.reader

import okio.FileSystem
import okio.Path
import ro.dragossusi.mobile.strings.reader.SourceReader.readCsv

class FileReader(
    private val fileSystem: FileSystem
) {

    fun readCsv(path: Path): List<List<String>> {
        if (!fileSystem.exists(path)) {
            throw Exception("Inexistent file")
        }
        return fileSystem.read(path) {
            readCsv()
        }
    }

    fun writeFile(path: Path, content: String): Boolean {
        path.parent?.let { fileSystem.createDirectories(it) }
        fileSystem.write(path) {
            writeUtf8(content)
            flush()
        }
        return true
    }

}