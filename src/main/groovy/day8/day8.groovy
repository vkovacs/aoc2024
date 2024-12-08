package day8

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

def inputFile = new File("../../resources/day8/input")
def testInputFile = new File("../../resources/day8/input-test")
def testInputFile2 = new File("../../resources/day8/input-test2")

@ToString
@EqualsAndHashCode
class Coordinate {
    int x, y

    Coordinate(int x, int y) {
        this.x = x
        this.y = y
    }
}

def read(file) {
    def map = []
    file.readLines().each { line ->
        map << line.split("")
    }

    def coordinatesMap = [:]

    def maxX = map.size()
    def maxY = map[0].size()

    for (i in (0..<maxX)) {
        for (j in (0..<maxY)) {
            def s = map[i][j]
            if (s != ".") {
                coordinatesMap.computeIfAbsent(s) { [] } << new Coordinate(i, j)
            }
        }
    }

    [map, coordinatesMap]
}

def d(Coordinate c0, Coordinate c1) {
    [c0.x - c1.x, c0.y - c1.y]
}

def inRange(map, x, y) {
    def maxX = map.size()
    def maxY = map[0].size()

    x >= 0 && y >= 0 && x < maxX && y < maxY
}

def part1(map, coordinatesMap) {
    def nodeCoordinates = [] as Set

    coordinatesMap.each { k, v ->
        for (i in (0..<v.size())) {
            for (j in (0..<v.size())) {
                if (i != j) {
                    def (dx, dy) = d(v[i], v[j])
                    //println(v[i].toString() + " " + v[j].toString() + " " + dx + " " + dy)

                    n0x = v[i].x + dx
                    n0y = v[i].y + dy

                    if (inRange(map, n0x, n0y)) {
                        nodeCoordinates << new Coordinate(n0x, n0y)
                    }

                    n1x = v[i].x - 2 * dx
                    n1y = v[i].y - 2 * dy

                    if (inRange(map, n1x, n1y)) {
                        nodeCoordinates << new Coordinate(n1x, n1y)
                    }
                }
            }
        }
    }

    printMap(map, nodeCoordinates)

    return nodeCoordinates.size()
}

def part2(map, coordinatesMap) {
    def nodeCoordinates = [] as Set

    coordinatesMap.each { k, v ->
        for (i in (0..<v.size())) {
            for (j in (0..<v.size())) {
                if (i != j) {
                    def (dx, dy) = d(v[i], v[j])
                    //println(v[i].toString() + " " + v[j].toString() + " " + dx + " " + dy)
                    nodeCoordinates << new Coordinate(v[i].x, v[i].y)
                    nodeCoordinates << new Coordinate(v[j].x, v[j].y)

                    n0x = v[i].x
                    n0y = v[i].y
                    do {
                        n0x = n0x + dx
                        n0y = n0y + dy

                        if (inRange(map, n0x, n0y)) {
                            nodeCoordinates << new Coordinate(n0x, n0y)
                        }
                    } while (inRange(map, n0x, n0y))

                    n1x = v[i].x
                    n1y = v[i].y

                    do {
                        n1x = n1x - 2 * dx
                        n1y = n1y - 2 * dy

                        if (inRange(map, n1x, n1y)) {
                            nodeCoordinates << new Coordinate(n1x, n1y)
                        }
                    } while (inRange(map, n1x, n1y))
                }
            }
        }
    }

    printMap(map, nodeCoordinates)

    return nodeCoordinates.size()
}

private void printMap(map, Set nodeCoordinates) {
    for (i in (0..<map.size())) {
        for (j in (0..<map.size())) {
            if (new Coordinate(i, j) in nodeCoordinates) {
                print("#")
            } else print(".")
        }
        println()
    }
}

def testSolution1 = part1(*read(testInputFile))
println(testSolution1)
assert testSolution1 == 14

def solution1 = part1(*read(inputFile))
println(solution1)
assert solution1 == 293

def testSolution2 = part2(*read(testInputFile2))
println(testSolution2)
assert testSolution2 == 9

def solution2 = part2(*read(inputFile))
println(solution2)
assert solution2 == 934