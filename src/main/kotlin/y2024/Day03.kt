package y2024

import Day
import util.FileReader

fun main() = Day03.run()

object Day03: Day() {

    private val data = FileReader.readFile("y2024/Day03.txt")

    override fun part1(): Int {
        var sum = 0
        Regex("mul\\(\\d+,\\d+\\)").findAll(data).forEach {
            val numbers = Regex("\\d+").findAll(it.value)
            sum += numbers.first().value.toInt() * numbers.last().value.toInt()
        }
        return sum
    }

    override fun part2(): Any {
        var sum = 0
        var enabled = true
        Regex("mul\\(\\d+,\\d+\\)|don't\\(\\)|do\\(\\)").findAll(data).forEach {
            if (it.value == "don't()") {
                enabled = false
            } else if (it.value == "do()") {
                enabled = true
            } else if (enabled) {
                val numbers = Regex("\\d+").findAll(it.value)
                sum += numbers.first().value.toInt() * numbers.last().value.toInt()
            }
        }
        return sum
    }
}