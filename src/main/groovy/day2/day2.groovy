package day2

def inputFile = new File("../../resources/day2/input")
def testInputFile = new File("../../resources/day2/input-test")

def reports = inputFile.readLines().collect { it.split(" +").collect { it as int } }
def testReports = testInputFile.readLines().collect { it.split(" +").collect { it as int } }

def part1(def reports) {
    diffs(reports).count { levelDiffs ->
        isSafe(levelDiffs)
    }
}

def diffs(reports) {
    reports.collect { level ->
        diff(level)
    }
}
def diff(level) {
    (1..<level.size()).collect { i -> level[i] - level[i - 1] }
}


def isSafe(def levelDiffs) {
    levelDiffs.every { it > 0 && it < 4 } ||
            levelDiffs.every { it < 0 && it > -4 }
}

def part2(def reports) {
    reports.count( { report ->
        def levelDiff = diff(report)

        if (isSafe(levelDiff)) {
            return true
        }

        def indexToRemove = (0..<report.size()).find({index ->
            def resultAfterRemoveList = report.collect()
            resultAfterRemoveList.remove(index)

            isSafe(diff(resultAfterRemoveList))
        })

        return indexToRemove != null

    })
}

def solution1 = part1(reports)
println solution1
assert solution1 == 282

def testSolution2 = part2(testReports)
println testSolution2
assert testSolution2 == 4

def solution2 = part2(reports)
println solution2
assert solution2 == 349