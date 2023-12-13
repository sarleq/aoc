package util

import kotlin.math.abs
import kotlin.math.sqrt

object MathsUtil {
    fun calculateManhattanDistance(x1: Int, y1: Int, x2: Int, y2: Int) = abs(x2 - x1) + abs(y2 - y1)

    fun transpose(array: MutableList<CharArray>): MutableList<CharArray> {
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
}