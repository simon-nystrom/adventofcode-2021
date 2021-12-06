import kotlin.io.path.Path
import kotlin.io.path.readLines

class Day01 {
    companion object {
        fun part1(input: List<Int>): Int =
            input.windowed(2).count { it[1] > it[0] }
        
        fun part2(input: List<Int>): Int =
            input.windowed(3)
                .windowed(2)
                .count { it[1].sum() > it[0].sum() }
    }
}


fun main(args: Array<String>) {
    val input = Path("inputs/day01.txt").readLines().map { it.toInt() }
    println(Day01.part1(input))
    println(Day01.part2(input))
}