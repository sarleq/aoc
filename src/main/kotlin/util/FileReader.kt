package util

import java.io.File

object FileReader {
    fun readFile(path: String) = File(ClassLoader.getSystemResource(path).file).readText()
    fun readAsStrings(path: String) = readFile(path).split("\n")
    fun readAsCharArrays(path: String) = readAsStrings(path).map { it.toCharArray() }
    fun readAsInts(path: String) = readAsStrings(path).map { it.toInt() }

}