package util

import kotlin.math.abs

object MathsUtil {
    fun calculateManhattanDistance(x1: Int, y1: Int, x2: Int, y2: Int) = abs(x2 - x1) + abs(y2 - y1)

    fun transposeCharArray(array: List<CharArray>): List<CharArray> {
        val width = array.size
        val height = array[0].size
        val newArray = MutableList(height) { CharArray(width) }
        for (x in 0..<width) {
            for (y in 0..<height) {
                newArray[y][x] = array[x][y]
            }
        }
        return newArray
    }

    fun transpose(array: List<List<Any>>): List<List<Any>> {
        val rows = array.size
        val cols = array[0].size
        return MutableList(cols) {j -> MutableList(rows) { i -> array[i][j] } }
    }
}