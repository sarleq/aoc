package y2023

import Day
import util.FileReader

fun main() = Day02.run()

object Day02: Day() {
    private val data = FileReader.readAsStrings("y2023/Day02.txt")

    override fun part1(): Int {
        val games = data.associate {
            val (left, right) = it.split(": ")
            left.split(" ")[1].toInt() to right.split("; ")
        }
        var sum = 0
        for ((key, value) in games) {
            var possible = true
            for (set in value) {
                val balls = set.split(", ")
                for (ball in balls) {
                    val (number, color) = ball.split(" ")
                    if (possible) possible = (color == "red" && number.toInt() <= 12) || (color == "green" && number.toInt() <= 13) || (color == "blue" && number.toInt() <= 14)
                }
            }
            if (possible) sum += key
        }
        return sum
    }

    override fun part2(): Int {
        val games = data.associate {
            val (left, right) = it.split(": ")
            left.split(" ")[1].toInt() to right.split("; ")
        }
        var sum = 0
        for (value in games.values) {
            var red = -1; var green = -1; var blue = -1
            for (set in value) {
                val balls = set.split(", ")
                for (ball in balls) {
                    val (number, color) = ball.split(" ")
                    when (color) {
                        "red" -> if (number.toInt() > red) red = number.toInt()
                        "green" -> if (number.toInt() > green) green = number.toInt()
                        "blue" -> if (number.toInt() > blue) blue = number.toInt()
                    }
                }
            }
            sum += red * green * blue
        }
        return sum
    }

}