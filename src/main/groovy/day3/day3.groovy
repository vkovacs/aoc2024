package day3

def inputFile = new File("../../resources/day3/input")
def testInputFile = new File("../../resources/day3/input-test")
def testInputFile2 = new File("../../resources/day3/input-test2")

def memoryString = inputFile.readLines().join("")
def testMemoryString = testInputFile.readLines().join("")
def testMemoryString2 = testInputFile2.readLines().join("")


def part1(memoryString) {
    def regex = /mul\(\d+,\d+\)/

    def matches = (memoryString =~ regex).collect()

    matches.collect { match ->
        def (a, b) = (match =~ /mul\((\d+),(\d+)\)/)[0][1, 2]*.toInteger()
        new Tuple2(a, b)
    }.inject(0) { result, x ->
        result + x[0] * x[1]
    }
}

def part2(memoryString) {
    def operationRegexp = /mul\(\d+,\d+\)|do\(\)|don't\(\)/

    var sum = 0
    var shouldDo = true

    memoryString.findAll(operationRegexp).each { match ->
        if (match == "do()") shouldDo = true
        else if (match == "don't()") shouldDo = false
        else if (shouldDo) {
            def (x,y) = match.findAll(/\d+/)*.toInteger()
            sum += x*y
        }
    }

    sum
}

def testSolution1 = part1(testMemoryString)
println testSolution1
assert  testSolution1 == 161

def solution1 = part1(memoryString)
println solution1
assert solution1 == 191183308

def testSolution2 = part2(testMemoryString2)
println testSolution2
def solution2 = part2(memoryString)
println solution2
assert solution2 == 92082041