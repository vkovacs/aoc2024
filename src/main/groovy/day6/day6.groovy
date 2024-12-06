package day6

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

def inputFile = new File("../../resources/day6/input")
def testInputFile = new File("../../resources/day6/input-test")

@ToString
@EqualsAndHashCode
class Coordinate {
    int x, y

    Coordinate(int x, int y) {
        this.x = x
        this.y = y
    }
}

@ToString
@EqualsAndHashCode
class Direction {
    int x, y
    String name

    Direction(String name, int x, int y) {
        this.name = name
        this.x = x
        this.y = y
    }
}

N = new Direction("N", -1, 0)
E = new Direction("E", 0, 1)
S = new Direction("S", 1, 0)
W = new Direction("W", 0, -1)

turn90 = [N: E, E: S, S: W, W: N]

def forwardCoordinate(currentCoordinate, direction, maxX, maxy) {
    forwardX = currentCoordinate.x + direction.x
    forwardY = currentCoordinate.y + direction.y
    if (forwardX >= 0 && forwardX < maxX && forwardY >= 0 && forwardY < maxy) {
        return new Coordinate(forwardX, forwardY)
    }
    null
}

def read(file) {

    def map = []
    file.readLines().each { line ->
        map << line.split("")
    }

    for (i in 0..<map.size()) {
        for (j in 0..<map[i].size()) {
            if (map[i][j] == "^") {
                return [map, new Coordinate(i, j)]
            }
        }
    }
}

def part1(map, start) {
    def maxX = map.size()
    def maxY = map[0].size()

    def visitedMap = []
    maxX.times { // Iterate n times for the rows
        visitedMap << ('.' * maxY).toList() // Create a row of m '.' and add it to the map
    }

    def direction = N
    def visitedCoordinates = [] as Set

    def current = start
    while (true) {
        def maybeNext = forwardCoordinate(current, direction, maxX, maxY)
        if (maybeNext == null) {
            visitedCoordinates << current
            return visitedCoordinates.size()
        }

        def maybeNextChar = map[maybeNext.x][maybeNext.y]
        if (maybeNextChar == ".") {
            visitedCoordinates << current
            map[current.x][current.y] = "."
            visitedMap[current.x][current.y] = "X"
            current = maybeNext
        } else {
            direction = turn90[direction.name]
        }
    }
}

@ToString
@EqualsAndHashCode
class VisitedPlace {
    Coordinate coordinate
    Direction direction

    VisitedPlace(Coordinate coordinate, Direction direction) {
        this.coordinate = coordinate
        this.direction = direction
    }
}

def isLoop(map, maxX, maxY, start) {
    def direction = N
    def visitedCoordinates = [] as Set

    def current = start
    while (!visitedCoordinates.contains(new VisitedPlace(current, direction))) {
        def maybeNext = forwardCoordinate(current, direction, maxX, maxY)
        if (maybeNext == null) {
            return false
        }

        def maybeNextChar = map[maybeNext.x][maybeNext.y]
        if (maybeNextChar == ".") {
            visitedCoordinates << new VisitedPlace(current, direction)
            map[current.x][current.y] = "."
            current = maybeNext
        } else {
            direction = turn90[direction.name]
        }
    }
    true
}

def part2(map, start) {
    def maxX = map.size()
    def maxY = map[0].size()

    def loopCount = 0

    for (i in (0..<maxX)) {
        if (i % 10 == 0) println "i: $i"
        for (j in (0..<maxY)) {
            if (map[i][j] == ".") {
                map[i][j] = "O"
                if (isLoop(map, maxX, maxY, start)) {
                    loopCount++
                }
                map[i][j] = "."
            }
        }
    }

    loopCount
}

testSolution1 = part1(*read(testInputFile))
println testSolution1
assert testSolution1 == 41

solution1 = part1(*read(inputFile))
println solution1
assert solution1 == 4890

testSolution2 = part2(*read(testInputFile))
println testSolution2
assert testSolution2 == 6

solution2 = part2(*read(inputFile))
println solution2
assert solution2 == 1995