package y2024

import Day
import util.FileReader

fun main() = Day02.run()

object Day02: Day() {

    private val data = FileReader.readAsIntLists("y2024/Day02.txt")

    override fun part1(): Int = data.sumOf { if (isSafeReport(it)) 1 as Int else 0 }

    override fun part2(): Int {
        var safe = 0
        data.forEach {
            if (isSafeReport(it)) {
                safe++
            } else {
                for (i in it.indices) {
                    val report = it.toMutableList()
                    report.removeAt(i)
                    if (isSafeReport(report)) {
                        safe++
                        break
                    }
                }
            }
        }
        return safe
    }

    private fun isSafeReport(report: List<Int>): Boolean {
        if (report[0] > report[1]) { //decrease
            for (i in 0..<report.indices.last) {
                if (report[i] - report[i+1] !in 1..3) {
                    return false
                }
            }
            return true
        }
        else if (report[0] < report[1]) { //increase
            for (i in 0..<report.indices.last) {
                if (report[i+1] - report[i] !in 1..3) {
                    return false
                }
            }
            return true
        }
        return false
    }
}