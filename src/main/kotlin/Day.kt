import kotlin.system.measureNanoTime

abstract class Day {
    fun run() {
        println("It took ${measureNanoTime { println("Part 1: ${part1()}") }} ns!\n")
        println("It took ${measureNanoTime { println("Part 2: ${part2()}") }} ns!\n")
    }

    abstract fun part1(): Any
    abstract fun part2(): Any
}