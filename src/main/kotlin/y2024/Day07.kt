package y2024

import Day
import util.FileReader

fun main() = Day07.run()

object Day07: Day() {

    private val data = FileReader.readAsStrings("y2024/Day07.txt")
    private val equations: List<Pair<Long, List<Long>>> = data.map { it.split(": ") }
        .map { line -> line[0].toLong() to line[1].split(" ").map { it.toLong() } }

    override fun part1() = calculateEquations(false)

    override fun part2() = calculateEquations(true)

    private fun calculateEquations(partTwo: Boolean): Long {
        var correctEquations = 0L
        for (equation in equations) {
            var calculations = mutableListOf(equation.second[0])
            for (i in 1..equation.second.indices.last) {
                val newCalculations = mutableListOf<Long>()
                for (calculation in calculations) {
                    newCalculations.add(calculation + equation.second[i])
                    newCalculations.add(calculation * equation.second[i])
                    if (partTwo) {
                        newCalculations.add( (calculation.toString() + equation.second[i].toString()).toLong() )
                    }
                }
                calculations = newCalculations
            }
            if (calculations.contains(equation.first)) { correctEquations += equation.first }
        }
        return correctEquations
    }
}