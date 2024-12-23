package day4

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

def inputFile = new File("../../resources/day4/input")
def testInputFile = new File("../../resources/day4/input-test")
def testInputFile2 = new File("../../resources/day4/input-test2")

@ToString
@EqualsAndHashCode
class Coordinate {
    int x, y

    Coordinate(int x, int y) {
        this.x = x
        this.y = y
    }
}

directions =
        [new Coordinate(-1, -1), new Coordinate(0, -1), new Coordinate(1, -1),
         new Coordinate(-1, 0), new Coordinate(1, 0),
         new Coordinate(-1, 1), new Coordinate(0, 1), new Coordinate(1, 1)]

def part1(input, word) {
    matrix = [][]
    input.readLines().each {
        matrix << it.split("")
    }

    def maxRows = matrix.size()
    def maxCols = matrix[0].size()
    wordOccurrenceCount = 0

    maxRows.times { rowIndex ->
        maxCols.times { colIndex ->
            directions.each { direction ->
                wordOccurrenceCount += walk(matrix, rowIndex, colIndex, [direction], 0, word, [])

            }
        }
    }
    wordOccurrenceCount
}

def walk(matrix, x, y, directions, letterIndexToFind, word, visited) {
    def currentLetter = matrix[x][y]
    def count = 0
    if (letterIndexToFind < word.size() && currentLetter == word[letterIndexToFind]) {
        if (letterIndexToFind == word.size() - 1) {
            //println(visited << [x, y])
            return 1
        }

        def nextLetterIndex = ++letterIndexToFind

        def neighbours = neighbours(x, y, directions)
        def newVisited = (visited << [x, y])
        neighbours.each { neighbour ->
            count += walk(matrix, neighbour.x, neighbour.y, directions, nextLetterIndex, word, newVisited)
        }
    }

    count
}

def neighbours(int i, int j, directions) {
    Set<Coordinate> neighbours = []
    directions.each { direction ->
        neighbourX = i + direction.x
        neighbourY = j + direction.y
        if (neighbourX >= 0 && neighbourX < matrix.size() && neighbourY >= 0 && neighbourY < matrix[i].size()) {
            neighbours << new Coordinate(neighbourX, neighbourY)
        }
    }
    neighbours
}

def part2(input) {
    matrix = [][]
    input.readLines().each {
        matrix << it.split("")
    }

    def maxRows = matrix.size()
    def maxCols = matrix[0].size()
    xMasOccurrenceCount = 0

    def NW = new Coordinate(-1, -1)
    def NE = new Coordinate(-1, 1)
    def SW = new Coordinate(1, -1)
    def SE = new Coordinate(1, 1)

    def directions = [NW, NE, SW, SE]

    maxRows.times { i ->
        maxCols.times { j ->
            if (matrix[i][j] == "A") {
                if (neighbours(i, j, directions).size() == 4) {
                    if ((walk(matrix, i + NW.x, j + NW.y, [SE], 0, "MAS", []) > 0 ||
                            walk(matrix, i + NW.x, j + NW.y, [SE], 0, "SAM", []) > 0) &&
                            (walk(matrix, i + NE.x, j + NE.y, [SW], 0, "MAS", []) > 0 ||
                            walk(matrix, i + NE.x, j + NE.y, [SW], 0, "SAM", []) > 0)) {
                        xMasOccurrenceCount++
                    }
                }
            }
        }
    }
    xMasOccurrenceCount
}

def testSolution1 = part1(testInputFile, "XMAS")
println(testSolution1)
testSolution1 == 18

def solution1 = part1(inputFile, "XMAS")
println(solution1)
solution1 == 2633

def testSolution2 = part2(testInputFile2)
println(testSolution2)
assert testSolution2 == 9

def solution2 = part2(inputFile)
println(solution2)
assert solution2 == 1936
