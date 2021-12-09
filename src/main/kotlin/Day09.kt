import kotlin.io.path.Path
import kotlin.io.path.readLines

class Day09 {
    companion object {

        private fun isLowPoint(x: Int, y: Int, grid: List<List<Int>>): Boolean {
            var lowest = true
            if (y - 1 > 0) lowest = lowest && grid[y][x] < grid[y - 1][x]
            if (y < grid.size - 1) lowest = lowest && grid[y][x] < grid[y + 1][x]
            if (x > 0) lowest = lowest && grid[y][x] < grid[y][x - 1]
            if (x < grid[0].size - 1) lowest = lowest && grid[y][x] < grid[y][x + 1]
            if (y < grid.size - 1 && x < grid[0].size - 1) lowest = lowest && grid[y][x] < grid[y + 1][x + 1]
            if (y > 0 && x < grid[0].size - 1) lowest = lowest && grid[y][x] < grid[y - 1][x + 1]
            if (y > 0 && x < grid[0].size - 1) lowest = lowest && grid[y][x] < grid[y - 1][x + 1]
            if (y > 0 && x > 0) lowest = lowest && grid[y][x] < grid[y - 1][x - 1]
            return lowest
        }

        fun part1(input: List<List<Int>>): Any? {

            var risk = 0
            for (y in input.indices) {
                for (x in 0 until input[y].size) {
                    if (isLowPoint(x, y, input)) {

                        risk += input[y][x] + 1
                    }
                }
            }
            return risk
        }

        private fun explore(x: Int, y: Int, grid: List<List<Int>>, visited: MutableSet<String> = mutableSetOf()): Int {

            if (x > grid[0].size - 1 || y > grid.size - 1 || y < 0 || x < 0 || grid[y][x] == 9) return 0
            if ("$y,$x" in visited) return 0

            visited.add("$y,$x")
            
            if (x < grid[y].size) explore(x + 1, y, grid, visited)
            if (y < grid.size) explore(x, y + 1, grid, visited)
            if (x > 0) explore(x - 1, y, grid, visited)
            if (y > 0) explore(x, y - 1, grid, visited)

            return visited.size

        }


        fun part2(input: List<List<Int>>): Any? {

            val basinSizes = mutableListOf<Int>()
            for (y in input.indices) {
                for (x in 0 until input[y].size) {
                    if (isLowPoint(x, y, input)) {
                        basinSizes.add(explore(x, y, input))
                    }
                }
            }

            return basinSizes.sorted().takeLast(3).reduce { acc, i -> acc * i }

        }
    }
}

fun main(args: Array<String>) {
    val input = Path("inputs/day09.txt").readLines().map { it.split("").drop(1).dropLast(1).map { it.toInt() } }
    println(Day09.part1(input))
    println(Day09.part2(input))
}