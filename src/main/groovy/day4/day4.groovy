package day4

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

def inputFile = new File("../../resources/day4/input")
def testInputFile = new File("../../resources/day4/input-test")

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

wordOccurrenceCount = 0

def part1(input, word) {
    matrix = [][]
    input.readLines().each {
        matrix << it.split("")
    }

    def maxRows = matrix.size()
    def maxCols = matrix[0].size()

    maxRows.times { rowIndex ->
        maxCols.times { colIndex ->
            wordOccurrenceCount += walk(matrix, rowIndex, colIndex, 0, word, [])
        }
    }
}

def walk(matrix, x, y, letterIndexToFind, word, visited) {
    def currentLetter = matrix[x][y]
    def count = 0
    if (letterIndexToFind < word.size() && currentLetter == word[letterIndexToFind]) {
        visited << [x,y]
        if (letterIndexToFind == word.size() - 1) {
            println(visited)
            return 1
        }

        def nextLetterIndex = ++letterIndexToFind

        def neighbours = neighbours(x, y)
        neighbours.each { neighbour ->
            count += walk(matrix, neighbour.x, neighbour.y, nextLetterIndex, word, visited)
        }
    }

    count
}

def neighbours(int i, int j) {
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

def part2() {

}

def testSolution1 = part1(testInputFile, "XMAS")
println(wordOccurrenceCount)