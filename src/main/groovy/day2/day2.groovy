package day2

def inputFile = new File("../../resources/day2/input")
def testInputFile = new File("../../resources/day2/input-test")

def reports = inputFile.readLines().collect { it.split(" +").collect { it as int } }


def part1(def reports) {
    var diffs = reports.collect { level ->
        (1..<level.size()).collect { i -> level[i] - level[i - 1] }
    }

    def count = diffs.count { levelDiffs ->
        levelDiffs.every { it > 0 && it < 4 } ||
                levelDiffs.every { it < 0 && it > -4 }
    }
}

def solution1 = part1(reports)
assert  solution1 == 282