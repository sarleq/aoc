package y2019

import Day
import util.FileReader
import kotlin.math.floor

fun main() = Day01.run()

object Day01: Day() {
    private val data = FileReader.readAsInts("y2019/Day01.txt")

    override fun part1() = data.sumOf { fuel -> calculateFuel(fuel.toDouble()) }

    override fun part2(): Double {
        var total = 0.0
        for (module in data) {
            var neededFuel = calculateFuel(module.toDouble())
            total += neededFuel
            while (calculateFuel(neededFuel) > 0.0) {
                neededFuel = calculateFuel(neededFuel)
                total += neededFuel
            }
        }
        return total
    }

    private fun calculateFuel(fuel: Double) = floor(fuel / 3) - 2
}