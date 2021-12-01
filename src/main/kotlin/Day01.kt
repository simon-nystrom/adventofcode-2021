import kotlin.io.path.Path
import kotlin.io.path.readLines

class Day01 {
    companion object {
        fun part1(): Int {
            return Path("inputs/day01.txt").readLines().map { it.toInt() }
                .windowed(2)
                .fold(0) { acc, window -> if (window[1] > window[0]) acc + 1 else acc }
        }

        fun part2(): Int {
            return Path("inputs/day01.txt").readLines().map { it.toInt() }
                .windowed(3)
                .windowed(2)
                .fold(0) { acc, windows -> if (windows[1].sum() > windows[0].sum()) acc + 1 else acc }
        }
    }
}



fun main(args: Array<String>) {
    println(Day01.part1())
    println(Day01.part2())
}