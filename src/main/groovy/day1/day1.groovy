package day1

def inputFile = new File("../../resources/day1/input")

def lines = inputFile.readLines()

//part1
def pq0 = new PriorityQueue()
def pq1 = new PriorityQueue()

lines.each { line ->
    def values = line.split(" +").collect { it.toInteger() } as int[]
    def (v0, v1) = values

    pq0.add(v0)
    pq1.add(v1)
}

def sumDistance = 0
lines.size().times {
    minPq0 = pq0.poll() as int
    minPq1 = pq1.poll() as int
    distance = Math.abs(minPq0 - minPq1) as int
    sumDistance += distance
}

println(sumDistance)

//part2

def left = []
def right = []
lines.each { line ->
    def values = line.split(" +").collect { it.toInteger() } as int[]
    def (v0, v1) = values
    left << v0
    right << v1
}

def rightFrequencyMap = right.countBy {it}

similarityScore = left.sum {
    it * (rightFrequencyMap[it] ?: 0)
}

println(similarityScore)