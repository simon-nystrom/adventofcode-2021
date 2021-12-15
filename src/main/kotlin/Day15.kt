import kotlin.io.path.Path
import kotlin.io.path.readLines

class Day15 {
    companion object {

        fun part1(input: List<List<Int>>): Int {
            val graph = buildGraph(input)
            return dijkstra(graph)
        }

        fun part2(input: List<List<Int>>): Int {

            val expandedGrid = input.map { it.toMutableList() }.toMutableList()

            repeat(input.size) { r ->
                repeat(4) { c ->
                    expandedGrid[r].addAll(
                        input[r].map { if (it + c + 1 > 9) it - 9 + c + 1 else it + c + 1 }
                    )
                }
            }

            repeat(4) { r ->
                repeat(input.size) { c ->
                    expandedGrid.add(expandedGrid[r * input.size + c].map { if (it + 1 > 9) 1 else it + 1 }
                        .toMutableList())
                }
            }

            val graph = buildGraph(expandedGrid)
            return dijkstra(graph)
        }

        data class Node(
            val x: Int,
            val y: Int,
            val weight: Int,
            val neighbors: List<Pair<Int, Int>>,
            var dist: Int = Int.MAX_VALUE,
        )

        private fun buildGraph(grid: List<List<Int>>): List<MutableList<Node>> {
            val graph = grid.mapIndexed { y, row ->
                row.mapIndexed { x, w ->
                    Node(x, y, w, listOf(
                        x - 1 to y,
                        x + 1 to y,
                        x to y + 1,
                        x to y - 1
                    ).filterNot { it.first < 0 || it.first > row.size - 1 || it.second < 0 || it.second > grid.size - 1 })
                }.toMutableList()
            }
            graph[0][0].dist = 0
            return graph
        }


        fun dijkstra(graph: List<MutableList<Node>>): Int {
            val incomplete = mutableSetOf(graph[0][0])
            val complete = mutableSetOf<Node>()

            while (incomplete.isNotEmpty()) {
                val m = incomplete.minByOrNull { it.dist }!!
                incomplete.remove(m)
                for (neighborCoords in m.neighbors) {
                    val n = graph[neighborCoords.second][neighborCoords.first]
                    if (n !in complete) {
                        if (m.dist + n.weight < n.dist) {
                            n.dist = m.dist + n.weight
                        }
                        incomplete.add(n)
                    }
                }
                complete.add(m)
            }

            return graph[graph.size - 1][graph[0].size - 1].dist
        }


    }
}

fun main(args: Array<String>) {
    val input = Path("inputs/day15.txt").readLines().map { it.map { it.digitToInt() } }
    println(Day15.part1(input))
    println(Day15.part2(input))
}
