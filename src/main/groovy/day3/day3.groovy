package day3

def inputFile = new File("../../resources/day3/input")
def testInputFile = new File("../../resources/day3/input-test")

def memoryString = inputFile.readLines().join("")
def testMemoryString = testInputFile.readLines().join("")


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

def testSolution1 = part1(testMemoryString)
println testSolution1
assert  testSolution1 == 161

def solution1 = part1(memoryString)
println solution1
assert solution1 == 191183308