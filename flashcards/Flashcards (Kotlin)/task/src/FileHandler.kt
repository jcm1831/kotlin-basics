package flashcards

import java.io.File

object FileHandler {
    private var importFilename: String = ""
    private var exportFilename: String = ""

    fun getFilenames(args: Array<String>) {
        if (args.isNotEmpty() && args.size % 2 == 0 && args.size <= 4) {
            getFilenamesImpl(args[0], args[1])
            if (args.size == 4) getFilenamesImpl(args[2], args[3])
        }
    }

    fun createImportFileFromInput(): File? {
        Logger.printlnAndLog("File name:")
        return createImportFile(Logger.readlnAndLog())
    }

    fun createImportFile() = createImportFile(importFilename)
    fun createExportFileFromInput(): File {
        Logger.printlnAndLog("File name:")
        return createExportFile(Logger.readlnAndLog())
    }

    fun createExportFile() : File? {
        return if (exportFilename.isNotEmpty()) {
            createExportFile(exportFilename)
        } else {
            null
        }
    }

    private fun createImportFile(filename: String): File? {
        val file = File(filename)
        return if (file.exists()) file else null
    }

    private fun createExportFile(filename: String): File {
        val file = File(filename)
        if (file.exists()) file.delete()
        file.createNewFile()
        return file
    }

    private fun getFilenamesImpl(argName: String, argValue: String) {
        when (argName) {
            "-import" -> importFilename = argValue
            "-export" -> exportFilename = argValue
        }
    }
}