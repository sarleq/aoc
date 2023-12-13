package y2023

import Day
import util.FileReader
import util.MathsUtil.transpose

fun main() = Day13.run()

object Day13: Day() {
    private val data = FileReader.readAsCharArrays("y2023/Day13.txt")
    private val patterns = formatPatterns()

    private fun formatPatterns(): MutableList<MutableList<CharArray>> {
        val patterns = mutableListOf<MutableList<CharArray>>()
        var tmpPattern = mutableListOf<CharArray>()
        for (line in data) {
            if (line.isEmpty()) {
                patterns.add(tmpPattern)
                tmpPattern = mutableListOf()
            } else tmpPattern.add(line)
        }
        return patterns
    }

    override fun part1(): Int {
        var rows = 0; var cols = 0
        for (pattern in patterns) {
            var col = 0
            val row = findSymmetry(pattern)
            if (row <= 0) {
                col = findSymmetry(transpose(pattern))
            }
            rows += row
            cols += col
        }
        return cols + rows*100
    }

    override fun part2(): Int {
        var rows = 0; var cols = 0
        for (pattern in patterns) {
            var col = 0
            val row = findSymmetryWithSmudge(pattern)
            if (row <= 0) {
                col = findSymmetryWithSmudge(transpose(pattern))
            }
            rows += row
            cols += col
        }
        return cols + rows*100
    }

    private fun findSymmetry(pattern: MutableList<CharArray>): Int {
        var row = 0
        for (i in 0..<pattern.indices.last) {
            var isSymmetric = true
            val topPart = pattern.slice(0..i)
            val bottomPart = pattern.slice(i+1..pattern.indices.last)
            if (topPart.size < bottomPart.size) {
                var k = 0
                for (j in topPart.indices.last downTo 0) {
                    if (!topPart[j].contentEquals(bottomPart[k])) {
                        isSymmetric = false
                        break
                    }
                    else k++
                }
            } else {
                var k = topPart.indices.last
                for (j in bottomPart.indices) {
                    if (!bottomPart[j].contentEquals(topPart[k])) {
                        isSymmetric = false
                        break
                    }
                    else k--
                }
            }
            if (isSymmetric) {
                row = topPart.size
                break
            }
        }
        return row
    }

    private fun findSymmetryWithSmudge(pattern: MutableList<CharArray>): Int {
        var row = 0
        for (i in 0..<pattern.indices.last) {
            var isSymmetric = true
            var smudge = false
            val topPart = pattern.slice(0..i)
            val bottomPart = pattern.slice(i+1..pattern.indices.last)
            if (topPart.size < bottomPart.size) {
                var k = 0
                for (j in topPart.indices.last downTo 0) {
                    if (topPart[j].contentEquals(bottomPart[k])) k++
                    else if (hasOneDifference(topPart[j], bottomPart[k]) && !smudge) {
                        smudge = true
                        k++
                    }
                    else {
                        isSymmetric = false
                        break
                    }
                }
            } else {
                var k = topPart.indices.last
                for (j in bottomPart.indices) {
                    if (bottomPart[j].contentEquals(topPart[k])) k--
                    else if (hasOneDifference(bottomPart[j], topPart[k]) && !smudge) {
                        smudge = true
                        k--
                    } else {
                        isSymmetric = false
                        break
                    }
                }
            }

            if (isSymmetric && smudge) {
                row = topPart.size
                break
            }
        }
        return row
    }

    private fun hasOneDifference(line1: CharArray, line2: CharArray): Boolean {
        var differences = 0
        for (i in line1.indices) {
            if (line1[i] != line2[i]) differences++
        }
        return differences == 1
    }


}