package day7

def inputFile = new File("../../resources/day7/input")
def testInputFile = new File("../../resources/day7/input-test")

def read(source) {
    source.readLines().collect { it.replace(":", "").split(" ")*.toLong() }
}

def operatorCombinations(operators, length) {
    def combinations = [""]

    length.times {
        def tmpCombinations = []
        operators.each { operator ->
            combinations.each { combination ->
                tmpCombinations << combination + operator
            }
        }
        combinations = tmpCombinations
    }
    combinations
}


def calculate(operators, numbers) {
    def tmpNumbers = numbers.collect() as List
    def tmpOperators = operators.split("") as List

    while (tmpNumbers.size() > 1) {
        def first = tmpNumbers.remove(0)
        def second = tmpNumbers.remove(0)
        def operator = tmpOperators.remove(0)

        def product = switch (operator) {
            case "+" -> first + second
            case "*" -> first * second
            case "|" -> (first.toString() + second.toString()).toLong()
        }

        tmpNumbers.add(0, product)
    }

    tmpNumbers[0]
}

def part1(input, ops) {
    long sum = 0

    for (line in input) {
        println(line)
        def expectedProduct = line.first()
        def numbers = line.drop(1)

        def operators = operatorCombinations(ops, numbers.size() - 1)

        for (o in operators) {
            def product = calculate(o, numbers)
            if (product == expectedProduct) {
                sum += product
                break
            }
        }
    }
    sum
}

def testSolution1 = part1(read(testInputFile), ["+","*"])
println(testSolution1)
assert testSolution1 == 3749

def solution1 = part1(read(inputFile), ["+","*"])
println(solution1)
assert solution1 == 3351424677624

def testSolution2 = part1(read(testInputFile), ["+", "*", "|"])
println(testSolution2)
assert testSolution2 == 11387

def solution2 = part1(read(inputFile), ["+", "*", "|"])
println(solution2)
assert solution2 == 204976636995111