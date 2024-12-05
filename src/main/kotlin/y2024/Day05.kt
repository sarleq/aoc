package y2024

import Day
import util.FileReader

fun main() = Day05.run()

object Day05: Day() {

    private val data = FileReader.readAsStrings("y2024/Day05.txt")
    private val rules = mutableListOf<Pair<Int, Int>>()
    private val updates = mutableListOf<List<Int>>()

    override fun part1() = updates.filter { isRightOrder(it) }.sumOf { it[it.indices.last/2] }

    override fun part2(): Int {
        var result = 0
        updates.filter { !isRightOrder(it) }.forEach {
            var newUpdate = it.toMutableList()
            while (!isRightOrder(newUpdate)) {
                val conflictingRule = getConflictingRule(newUpdate)
                newUpdate = swapPages(newUpdate, conflictingRule.first, conflictingRule.second)
            }
            result += newUpdate[newUpdate.indices.last/2]
        }
        return result
    }

    override fun init() {
        formatData()
    }

    private fun formatData() {
        for (line in data) {
            if (line.contains("|")) rules.add(line.split("|")[0].toInt() to line.split("|")[1].toInt())
            if (line.contains(",")) updates.add(line.split(",").map { it.toInt() })
        }
    }

    private fun isRightOrder(update: List<Int>): Boolean {
        var rightOrder = true
        for (rule in rules) {
            if (update.contains(rule.first) && update.contains(rule.second)) {
                if (update.indexOf(rule.first) > update.indexOf(rule.second)) {
                    rightOrder = false
                    break
                }
            }
        }
        return rightOrder
    }

    private fun getConflictingRule(update: List<Int>): Pair<Int, Int> {
        for (rule in rules) {
            if (update.contains(rule.first) && update.contains(rule.second)) {
                if (update.indexOf(rule.first) > update.indexOf(rule.second)) {
                    return rule
                }
            }
        }
        return -1 to -1
    }

    private fun swapPages(update: List<Int>, page1: Int, page2: Int): MutableList<Int> {
        val newUpdate = update.toMutableList()
        newUpdate[update.indexOf(page1)] = page2
        newUpdate[update.indexOf(page2)] = page1
        return newUpdate
    }
}