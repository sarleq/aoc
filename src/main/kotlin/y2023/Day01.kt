package y2023

import Day
import util.FileReader

fun main() = Day01.run()

object Day01: Day() {
    private val file = FileReader.readAsStrings("y2023/Day01.txt")
    private val reg = Regex("\\d|one|two|three|four|five|six|seven|eight|nine")
    private val regRev = Regex("\\d|eno|owt|eerht|ruof|evif|xis|neves|thgie|enin")

    override fun part1() = file.map { it.replace(Regex("[^0-9]"), "") }
            .sumOf { if (it.length == 1) (it + it).toInt() else (it.first().toString() + it.last()).toInt() }

    private fun toDigits(str: String) = when (str) {
        "one" -> 1
        "two" -> 2
        "three" -> 3
        "four" -> 4
        "five" -> 5
        "six" -> 6
        "seven" -> 7
        "eight" -> 8
        "nine" -> 9
        else -> str.toInt()
    }

    override fun part2() = file.sumOf { (toDigits(reg.find(it)!!.value) + toDigits(regRev.find(it.reversed())!!.value.reversed())) }
}