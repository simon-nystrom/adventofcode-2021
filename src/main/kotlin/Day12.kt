import kotlin.io.path.Path
import kotlin.io.path.readLines

class Day12 {
    companion object {

        fun traverse(
            src: String,
            dst: String,
            visitedSmallCaves: MutableSet<String>,
            map: Map<String, Set<String>>,
        ): Int {

            if (src in visitedSmallCaves) return 0
            if (src == dst) return 1
            if (!src.first().isUpperCase()) visitedSmallCaves.add(src)

            var numPaths = 0
            for (connection in map[src]!!) {
                numPaths += traverse(connection, dst, visitedSmallCaves.toMutableSet(), map)
            }
            return numPaths
        }

        val paths = mutableSetOf<String>()
        fun traverse2(
            src: String,
            dst: String,
            visitedSmallCaves: MutableMap<String, Int>,
            visitTwice: String,
            map: Map<String, Set<String>>,
            path: String
        ) {

            if (src in visitedSmallCaves) {
                if (src == visitTwice) {
                    if (visitedSmallCaves[src]!! >= 2) return
                } else return
            }

            if (src == dst) paths.add(path)

            if (!src.first().isUpperCase()) {
                visitedSmallCaves.putIfAbsent(src, 0)
                visitedSmallCaves[src] = visitedSmallCaves[src]!! + 1
            }
            for (connection in map[src]!!) {
                traverse2(connection, dst, visitedSmallCaves.toMutableMap(), visitTwice, map, "$path -> $connection")
            }
        }

        fun part1(input: MutableMap<String, MutableSet<String>>): Int {
            return traverse("start", "end", mutableSetOf(), input)
        }

        fun part2(input: MutableMap<String, MutableSet<String>>): Int {
            for (k in input.keys.filter { it != "start" && it != "end" }) {
                if (k.first().isLowerCase())
                    traverse2("start", "end", mutableMapOf(), k, input, "start")
            }
            return paths.size
        }
    }
}

fun main(args: Array<String>) {
    val input = Path("inputs/day12.txt").readLines().map { it.split("-") }

    val map: MutableMap<String, MutableSet<String>> = mutableMapOf()
    for (e in input) {
        map.putIfAbsent(e.last(), mutableSetOf(e.first()))
        map[e.last()]!!.add(e.first())
        map.putIfAbsent(e.first(), mutableSetOf(e.last()))
        map[e.first()]!!.add(e.last())
    }

    println(Day12.part1(map))
    println(Day12.part2(map))
}