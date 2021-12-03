import kotlin.io.path.Path
import kotlin.io.path.readLines

class Day01 {
    companion object {
        fun part1(): Int {
            return Path("inputs/day01.txt").readLines().map { it.toInt() }
                .windowed(2)
                .count { it[1] > it[0] }
        }

        fun part2(): Int {
            return Path("inputs/day01.txt").readLines().map { it.toInt() }
                .windowed(3)
                .windowed(2)
                .count { it[1].sum() > it[0].sum() }
        }
    }
}


fun main(args: Array<String>) {
    println(Day01.part1())
    println(Day01.part2())
}