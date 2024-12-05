package util

class Grid<T>(private val values: List<List<T>>) {

    val xIndices = values[0].indices
    val yIndices = values.indices
    val maxX = xIndices.last
    val maxY = yIndices.last

    fun get(coord: Pair<Int, Int>) = values[coord.first][coord.second]

    fun getNeighbour(x: Int, y: Int, direction: Direction): T? {
        when (direction) {
            Direction.UP -> if (y > 0) return values[y-1][x]
            Direction.DOWN -> if (y < maxY) return values[y+1][x]
            Direction.LEFT -> if (x > 0) return values[y][x-1]
            Direction.RIGHT -> if (x < maxX) return values[y][x+1]
            Direction.UPLEFT -> if (y > 0 && x > 0) return values[y-1][x-1]
            Direction.UPRIGHT -> if (y > 0 && x < maxX) return values[y-1][x+1]
            Direction.DOWNLEFT -> if (y < maxY && x > 0) return values[y+1][x-1]
            Direction.DOWNRIGHT -> if (y < maxY && x < maxX) return values[y+1][x+1]
        }
        return null
    }

}

enum class Direction {
    UP, DOWN, LEFT, RIGHT, UPLEFT, UPRIGHT, DOWNLEFT, DOWNRIGHT
}