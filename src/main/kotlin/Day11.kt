import kotlin.io.path.Path
import kotlin.io.path.readLines

class Day11 {
    companion object {

        private fun doFlash(
            x: Int,
            y: Int,
            grid: List<MutableList<Int>>,
            toFlash: MutableListIterator<Pair<Int, Int>>,
        ) {

            if (y > 0) {
                grid[y - 1][x] = grid[y - 1][x] + 1

                if (grid[y - 1][x] > 9) {
                    toFlash.add(Pair(x, y - 1))
                    toFlash.previous()
                }
            }

            if (y < grid.size - 1) {
                grid[y + 1][x] = grid[y + 1][x] + 1
                if (grid[y + 1][x] > 9) {
                    toFlash.add(Pair(x, y + 1))
                    toFlash.previous()
                }
            }

            if (x > 0) {
                grid[y][x - 1] = grid[y][x - 1] + 1
                if (grid[y][x - 1] > 9) {
                    toFlash.add(Pair(x - 1, y))
                    toFlash.previous()
                }
            }

            if (x < grid[0].size - 1) {
                grid[y][x + 1] = grid[y][x + 1] + 1
                if (grid[y][x + 1] > 9) {
                    toFlash.add(Pair(x + 1, y))
                    toFlash.previous()
                }
            }

            if (y < grid.size - 1 && x < grid[0].size - 1) {
                grid[y + 1][x + 1] = grid[y + 1][x + 1] + 1
                if (grid[y + 1][x + 1] > 9) {
                    toFlash.add(Pair(x + 1, y + 1))
                    toFlash.previous()
                }
            }

            if (y > 0 && x < grid[0].size - 1) {
                grid[y - 1][x + 1] = grid[y - 1][x + 1] + 1
                if (grid[y - 1][x + 1] > 9) {
                    toFlash.add(Pair(x + 1, y - 1))
                    toFlash.previous()
                }
            }

            if (y < grid.size - 1 && x > 0) {
                grid[y + 1][x - 1] = grid[y + 1][x - 1] + 1
                if (grid[y + 1][x - 1] > 9) {
                    toFlash.add(Pair(x - 1, y + 1))
                    toFlash.previous()
                }
            }

            if (y > 0 && x > 0) {
                grid[y - 1][x - 1] = grid[y - 1][x - 1] + 1
                if (grid[y - 1][x - 1] > 9) {
                    toFlash.add(Pair(x - 1, y - 1))
                    toFlash.previous()
                }
            }
        }


        fun part1(input: List<MutableList<Int>>): Any? {

            var numFlashes = 0
            for (i in 0..99) {
                val toFlash = mutableListOf<Pair<Int, Int>>()
                for (y in input.indices) {
                    for (x in 0 until input[y].size) {
                        input[y][x] = input[y][x] + 1
                        if (input[y][x] > 9) toFlash.add(Pair(x, y))
                    }
                }
                val didFlash = mutableSetOf<Pair<Int, Int>>()
                val it = toFlash.listIterator()
                while (it.hasNext()) {
                    val coord = it.next()

                    if (coord !in didFlash)
                        doFlash(coord.first, coord.second, input, it)
                    didFlash.add(coord)
                }
                numFlashes += didFlash.size
                for (y in input.indices) {
                    for (x in 0 until input[y].size) {
                        if (input[y][x] > 9) input[y][x] = 0
                    }
                }
            }
            return numFlashes
        }


        fun part2(input: List<MutableList<Int>>): Any? {

            var numSteps = 0
            while (input.sumOf { it.sum() } > 0) {
                numSteps++
                val toFlash = mutableListOf<Pair<Int, Int>>()
                for (y in input.indices) {
                    for (x in 0 until input[y].size) {
                        input[y][x] = input[y][x] + 1
                        if (input[y][x] > 9) toFlash.add(Pair(x, y))
                    }
                }
                val didFlash = mutableSetOf<Pair<Int, Int>>()
                val it = toFlash.listIterator()
                while (it.hasNext()) {
                    val coord = it.next()
                    if (coord !in didFlash)
                        doFlash(coord.first, coord.second, input, it)
                    didFlash.add(coord)
                }
                for (y in input.indices) {
                    for (x in 0 until input[y].size) {
                        if (input[y][x] > 9) input[y][x] = 0
                    }
                }
            }
            return numSteps
        }
    }
}

fun main(args: Array<String>) {
    val input = Path("inputs/day11.txt").readLines()
        .map { it.split("").drop(1).dropLast(1).map { it.toInt() }.toMutableList() }
    println(
        Day11.part1(input.map { it.toMutableList() })
    )
    println(
        Day11.part2(input.map { it.toMutableList() })
    )
}