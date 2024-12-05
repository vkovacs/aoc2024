package day5

def inputFile = new File("../../resources/day5/input")
def testInputFile = new File("../../resources/day5/input-test")

def read(inputFile) {
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
    [orderingRules, updates]
}

def part1(orderingRules, updates) {
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

def part2(def orderingRules, def updates) {
    updates.findAll { update ->
        !isValidOrder(orderingRules, update)
    }.collect { invalidUpdate ->
        order(orderingRules, invalidUpdate, [])
    }.collect { orderedUpdate ->
        orderedUpdate[orderedUpdate.size() / 2]
    }.inject(0) { result, x ->
        result += x
    }
}

def order(def rules, def update, def orderedUpdate) {
    if (update.size() == 1) {
        orderedUpdate << update.first()

        if (isValidOrder(rules, orderedUpdate)) return orderedUpdate
        else return order(rules, orderedUpdate, [])
    }

    def (first, second) = update[0..1]

    if (first in rules && second in rules[first]) {
        update.remove(0)
        return order(rules, update, orderedUpdate << first)
    } else {
        update.remove(1)
        return order(rules, update, orderedUpdate << second)
    }
}

def solution1 = part1(*read(inputFile))
println solution1
assert solution1 == 5374

def solution2 = part2(*read(inputFile))
println solution2
assert solution2 == 4260