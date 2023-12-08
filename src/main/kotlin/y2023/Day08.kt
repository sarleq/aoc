package y2023

import Day
import util.FileReader
import y2023.Day08.formatNodes
import java.math.BigInteger

fun main() {
    formatNodes()
    Day08.run()
}

object Day08: Day() {
    private val data = FileReader.readAsStrings("y2023/Day08.txt")
    private val instructions = data[0].toCharArray()
    private val nodes = mutableListOf<Node>()

    override fun part1(): Int {
        var currentNode = nodes.find { it.node == "AAA" }
        var i = 0; var steps = 0
        while (currentNode!!.node != "ZZZ") {
            currentNode = findNextNode(currentNode, instructions[i])
            if (i == instructions.indices.last) i = 0 else i++
            steps++
        }
        return steps
    }

    override fun part2(): BigInteger {
        var aNodes = nodes.filter { it.node.last() == 'A' }
        var allSteps = mutableListOf<BigInteger>()
        for (node in aNodes) {
            var currentNode = node
            var i = 0; var steps = BigInteger.ZERO
            while (currentNode.node.last() != 'Z') {
                currentNode = findNextNode(currentNode, instructions[i])!!
                if (i == instructions.indices.last) i = 0 else i++
                steps++
            }
            allSteps.add(steps)
        }
        return allSteps.reduce { acc, i -> leastCommonMultiple(acc, i) }
    }

    fun formatNodes() {
        for (i in data.indices) {
            if (i >= 2) {
                val (node, destinations) = data[i].split(" = ")
                val (destA, destB) = destinations.split(", ")
                nodes.add(Node(node, destA.replace("(", "") to destB.replace(")", "")))
            }
        }
    }

    private fun findNextNode(node: Node, instruction: Char) = nodes.find { it.node == if (instruction == 'L') node.destinations.first else node.destinations.second }

    private fun greatestCommonDivisor(a: BigInteger, b: BigInteger): BigInteger {
        var tmpB = b; var tmpA = a
        while (tmpB > BigInteger.ZERO) {
            val tmp = tmpB
            tmpB = tmpA % tmpB
            tmpA = tmp
        }
        return tmpA
    }

    private fun leastCommonMultiple(a: BigInteger, b: BigInteger) = a * b / greatestCommonDivisor(a, b)

}

class Node(val node: String, val destinations: Pair<String, String>)