package y2023

import Day
import util.FileReader
import util.MathsUtil

fun main() = Day14.run()
object Day14: Day() {
    private val data = FileReader.readAsStrings("y2023/Day14_test.txt")
    override fun part1() = moveStones(data).reversed().mapIndexed { index, line -> line.count { it == 'O' } * (index + 1) }.sum()

    override fun part2(): Any {
        var positions = moveStones(data, 'N')
        positions.forEach { println(it) }
        println(" ")
        positions = moveStones(data, 'W')
        positions.forEach { println(it) }
        println(" ")
        positions = moveStones(data, 'S')
        positions.forEach { println(it) }
        println(" ")
        positions = moveStones(data, 'E')
        positions.forEach { println(it) }
        println(" ")
        return -1
    }

    private fun moveStones(positions: List<String>, direction: Char = 'N'): List<String> {
        val endPositions = when (direction) {
            'S' -> positions.reversed().toMutableList()
            'E' -> MathsUtil.transposeCharArray(positions.reversed().map { it.toCharArray() }).map { String(it) }.toMutableList()
            'W' -> MathsUtil.transposeCharArray(positions.map { it.toCharArray() }).map { String(it) }.toMutableList()
            else -> positions.toMutableList()
        }
        for (x in 1..positions.indices.last) {
            for (y in positions[x].indices) {
                if (positions[x][y] == 'O') {
                    for (i in x-1 downTo 0) {
                        if (endPositions[i][y] == '.') {
                            endPositions[i+1] = endPositions[i+1].replaceRange(y, y+1, ".")
                            endPositions[i] = endPositions[i].replaceRange(y, y+1, "O")
                        } else break
                    }
                }
            }
        }
        return when (direction) {
            'S' -> endPositions.reversed()
            'E' -> MathsUtil.transposeCharArray(positions.reversed().map { it.toCharArray() }).map { String(it) }
            'W' -> MathsUtil.transposeCharArray(positions.map { it.toCharArray() }).map { String(it) }.toMutableList()
            else -> endPositions
        }
    }

}