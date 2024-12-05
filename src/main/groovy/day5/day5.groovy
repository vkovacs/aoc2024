package day5

def inputFile = new File("../../resources/day5/input")
def testInputFile = new File("../../resources/day5/input-test")

def part1(inputFile) {
    def orderingRules = [:]
    def updates = []

    inputFile.readLines().each { String line ->
        if (line.contains("|")) {
            def (key, value) = line.split("\\|")*.toInteger()
            orderingRules[key] = orderingRules.get(key, []) + value
        } else if (line.contains(",")) {
            updates << line.split(",")*.toInteger()
        }
    }

    updates.findAll { update ->
        isValidOrder(orderingRules, update)
    }.collect { update ->
        update[update.size() / 2]
    }.inject(0) { result, x ->
        result += x
    }
}

def isValidOrder(def rules, def update) {
    if (update.size() == 1) return true

    def first = update.first()
    def firstDropped = update.drop(1)
    def second = firstDropped.first()
    if (first in rules && second in rules[first])
        return isValidOrder(rules, firstDropped)
    else return false
}

def part2() {

}
def solution1 = part1(inputFile)
println solution1
assert solution1 == 5374

