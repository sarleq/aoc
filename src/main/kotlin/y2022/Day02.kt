package y2022

import Day
import util.FileReader

fun main() = Day02.run()

object Day02: Day() {
    private val file = FileReader.readAsStrings("y2022/Day02.txt")
    private val test = arrayOf("A Y", "B X", "C Z")

    // A / X -> ROCK
    // B / Y -> PAPER
    // C / Z -> SCISSOR

    override fun part1(): Int {
        return file.map { it.split(" ") }
            .map { (a, b) -> when(a to b) {
                "A" to "X" -> 4
                "A" to "Y" -> 8
                "A" to "Z" -> 3
                "B" to "X" -> 1
                "B" to "Y" -> 5
                "B" to "Z" -> 9
                "C" to "X" -> 7
                "C" to "Y" -> 2
                "C" to "Z" -> 6
                else -> { error(a to b) }
        } }.sum()
    }

    override fun part2(): Int {
        return file.map { it.split(" ") }
            .map { (a, b) -> when(a to b) {
                "A" to "X" -> 0 + 3
                "A" to "Y" -> 3 + 1
                "A" to "Z" -> 6 + 2
                "B" to "X" -> 0 + 1
                "B" to "Y" -> 3 + 2
                "B" to "Z" -> 6 + 3
                "C" to "X" -> 0 + 2
                "C" to "Y" -> 3 + 3
                "C" to "Z" -> 6 + 1
                else -> { error(a to b) }
            } }.sum()
    }
}