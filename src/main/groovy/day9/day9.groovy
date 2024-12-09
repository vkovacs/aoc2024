package day9

def inputFile = new File("../../resources/day9/input")

List<Integer> inputBlocks(String diskMap) {
    def blocks = []

    def blockId = 0
    def isBlock = true
    diskMap.split("").each { n ->
        if (isBlock) {
            (n as int).times {
                blocks << blockId
            }
            blockId++
        } else {
            (n as int).times {
                blocks << -1
            }
        }
        isBlock = !isBlock
    }

    blocks
}

List<Integer> order(List<Integer> blocks) {
    def i = 0
    def j = blocks.size() - 1

    while (i < j) {
        if (blocks[i] != -1) {
            i++
        } else if (blocks[j] == -1) {
            j--
        } else {
            blocks[i] = blocks[j]
            blocks[j] = -1
        }
    }

    blocks[0..<j]
}

BigInteger checksum(List<Integer> orderedBlocks) {
    BigInteger sum = 0
    def i = 0
    while (i < orderedBlocks.size()) {
        sum = sum + (orderedBlocks[i] * i)
        i++
    }

    return sum
}

def part1(input) {
    checksum(order(inputBlocks(input)))
}

/part1/

def input = inputFile.readLines()[0]

assert part1(input) == 6378826667552