package day10

def inputFile = new File("../../resources/day10/input")
def testInputFile = new File("../../resources/day10/input-test")


def read(input) {
    def map = []
    input.readLines().each { line ->
        map << line.split("")*.toInteger()
    }
    map
}

def neighbours(def matrix, int i, int j, directions) {
    def neighbours = []
    directions.each { direction ->
        neighbourX = i + direction[0]
        neighbourY = j + direction[1]
        if (neighbourX >= 0 && neighbourX < matrix.size() && neighbourY >= 0 && neighbourY < matrix[i].size()) {
            neighbours << [neighbourX, neighbourY]
        }
    }
    neighbours
}

def score(map, i, j, currentHeight, visited) {
    if (currentHeight == 9) return 1

    def directions = [[-1, 0], [0, 1], [1, 0], [0, -1]]
    def validNeighbours = neighbours(map, i, j, directions).findAll { n -> map[n[0]][n[1]] == currentHeight + 1 && !visited.contains([n[0], n[1]]) }
    if (validNeighbours.isEmpty()) return 0

    def count = 0
    for (neighbour in validNeighbours) {
        visited << [neighbour[0], neighbour[1]]
        count += score(map, neighbour[0], neighbour[1], currentHeight + 1, visited)
    }

    count
}

def score2(map, i, j, currentHeight) {
    if (currentHeight == 9) return 1

    def directions = [[-1, 0], [0, 1], [1, 0], [0, -1]]
    def validNeighbours = neighbours(map, i, j, directions).findAll { n -> map[n[0]][n[1]] == currentHeight + 1 }
    if (validNeighbours.isEmpty()) return 0

    def count = 0
    for (neighbour in validNeighbours) {
        count += score2(map, neighbour[0], neighbour[1], currentHeight + 1)
    }

    count
}

def part1(map) {
    def sumScore = 0
    for (i in (0..<map.size())) {
        for (j in (0..map[i].size())) {
            if (map[i][j] == 0) {
                sumScore += score(map, i, j, 0, [] as Set)
            }
        }
    }
    sumScore
}

def part2(map) {
    def sumScore = 0
    for (i in (0..<map.size())) {
        for (j in (0..map[i].size())) {
            if (map[i][j] == 0) {
                sumScore += score2(map, i, j, 0)
            }
        }
    }
    sumScore
}

assert part1(read(testInputFile)) == 36
assert part1(read(inputFile)) == 607
assert part2(read(testInputFile)) == 81
assert part2(read(inputFile)) == 1384
