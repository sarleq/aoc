package y2022

import Day
import util.FileReader

fun main() = Day01.run()

object Day01: Day() {
    private val file = FileReader.readFile("y2022/Day01.txt")
    private val elves = file.split("\n\n").map { elf -> elf.split("\n").map { it.toInt() } }

    override fun part1(): Int {
        return elves.maxOf { it.sum() }
    }

    override fun part2(): Int {
        return elves.map { it.sum() }.sortedDescending().take(3).sum()
    }
}