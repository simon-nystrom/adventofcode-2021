import kotlin.io.path.Path
import kotlin.io.path.readLines

class Day04 {
    companion object {

        private fun checkWinner(board: List<MutableList<Int>>): Boolean {
            for ((col, row) in board.withIndex()) {
                if (row.sum() == -5) return true
                if (board.sumOf { it[col] } == -5) return true
            }
            return false
        }

        fun part1(): Int {
            val lines = Path("inputs/day04.txt").readLines()
            val nums = lines.first().split(",").map { it.toInt() }

            var bingoBoards = lines.asSequence()
                .drop(1)
                .filter { it.isNotEmpty() }
                .map { it.split("""\s+""".toRegex()) }
                .flatten()
                .filter { it.isNotEmpty() }
                .map { it.toInt() }
                .chunked(5) { it.toMutableList() }
                .chunked(5)
                .toList()

            for (num in nums) {
                for (board in bingoBoards) {
                    for (row in board) {
                        row.forEachIndexed { index, col ->
                            if (col == num) {
                                row[index] = -1
                            }
                        }
                    }
                    if (checkWinner(board))
                        return board.sumOf { it.sumOf { n -> if (n == -1) 0 else n } } * num
                }
            }

            return -1
        }


        fun part2(): Any? {
            val lines = Path("inputs/day04.txt").readLines()
            val nums = lines.first().split(",").map { it.toInt() }

            val bingoBoards = lines.asSequence().drop(1)
                .filter { it.isNotEmpty() }
                .map { it.split("""\s+""".toRegex()) }
                .flatten()
                .filter { it.isNotEmpty() }
                .map { it.toInt() }
                .windowed(5, 5) { it.toMutableList() }
                .windowed(5, 5).toList()

            val wonBoards = mutableSetOf<Int>()

            for (num in nums) {
                for ((boardIndex, board) in bingoBoards.withIndex()) {
                    for (row in board) {
                        row.forEachIndexed { index, col ->
                            if (col == num) {
                                row[index] = -1
                            }
                        }
                    }

                    if (boardIndex !in wonBoards && checkWinner(board)) {
                        wonBoards.add(boardIndex)
                    }

                    if (wonBoards.size == bingoBoards.size) {
                        return board.sumOf { it.sumOf { n -> if (n == -1) 0 else n } } * num
                    }
                }

            }
            return -1
        }
    }
}


fun main(args: Array<String>) {

    println(Day04.part1()!!)
    println(Day04.part2())
}