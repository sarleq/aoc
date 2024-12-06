package util

class Grid<T>(private val values: List<List<T>>) {

    val xIndices = values[0].indices
    val yIndices = values.indices
    val maxX = xIndices.last
    val maxY = yIndices.last

    fun get(coord: Pair<Int, Int>) = values[coord.first][coord.second]

    fun getCoord(item: T): Pair<Int, Int> {
        for (x in xIndices) {
            for (y in yIndices) {
                if (values[y][x] == item) return y to x
            }
        }
        return -1 to -1
    }

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


    fun getNeighbourCoord(x: Int, y: Int, direction: Direction): Pair<Int, Int> {
        when (direction) {
            Direction.UP -> if (y > 0) return y-1 to x
            Direction.DOWN -> if (y < maxY) return y+1 to x
            Direction.LEFT -> if (x > 0) return y to x-1
            Direction.RIGHT -> if (x < maxX) return y to x+1
            Direction.UPLEFT -> if (y > 0 && x > 0) return y-1 to x-1
            Direction.UPRIGHT -> if (y > 0 && x < maxX) return y-1 to x+1
            Direction.DOWNLEFT -> if (y < maxY && x > 0) return y+1 to x-1
            Direction.DOWNRIGHT -> if (y < maxY && x < maxX) return y+1 to x+1
        }
        return -1 to -1
    }

}

enum class Direction {
    UP, DOWN, LEFT, RIGHT, UPLEFT, UPRIGHT, DOWNLEFT, DOWNRIGHT
}