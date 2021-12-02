import kotlin.io.path.Path
import kotlin.io.path.readLines

class Day02 {
    companion object {
        fun part1(): Any {
            val mapped =
                Path("inputs/day02.txt").readLines()
                    .map { it.split(" ") }
                    .groupBy({ it.first() }, { it.last().toInt() })

            return mapped["forward"]!!.sum() * (mapped["down"]!!.sum() - mapped["up"]!!.sum())

        }

        fun part2(): Any {

            val submarine = object {
                var aim = 0
                var dist = 0
                var depth = 0
            }

            val mapped =
                Path("inputs/day02.txt").readLines()
                    .map { it.split(" ") }
                    .fold(submarine) { acc, list ->
                        val num = list.last().toInt()
                        when (list.first()) {
                            "forward" -> {
                                acc.dist += num
                                acc.depth += acc.aim * num
                            }
                            "up" -> acc.aim -= num
                            "down" -> acc.aim += num
                        }
                        acc
                    }

            return mapped.depth * mapped.dist

        }
    }
}


fun main(args: Array<String>) {
    println(Day02.part1())
    println(Day02.part2())
}