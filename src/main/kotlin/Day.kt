import kotlin.system.measureTimeMillis

abstract class Day {
    fun run() {
        println("Things to do before everything... Took ${measureTimeMillis { init() }} ms\n")
        println("It took ${measureTimeMillis { println("Part 1: ${part1()}") }} ms!\n")
        println("It took ${measureTimeMillis { println("Part 2: ${part2()}") }} ms!\n")
    }

    abstract fun part1(): Any
    abstract fun part2(): Any

    open fun init() { }
}