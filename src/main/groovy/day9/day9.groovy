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
        if (orderedBlocks[i] > -1) {
            sum = sum + (orderedBlocks[i] * i)
        }
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

/part2/

def ranges(list) {
    if (list.isEmpty()) return []

    def sublists = [] as LinkedList
    def currentSublist = [list[0]]

    list.tail().each { num ->
        if (num == currentSublist.last() as int) {
            currentSublist << num
        } else {
            sublists << currentSublist
            currentSublist = [num]
        }
    }

    sublists << currentSublist // Add the last sublist
    return sublists
}

def swap(ranges, i, j) {
    if (ranges[i].size() == ranges[j].size()) {
        def tmp = ranges[i]
        ranges[i] = ranges[j]
        ranges[j] = tmp
        return false
    } else if (ranges[j].size() < ranges[i].size()) {
        int sizeDiff = ranges[i].size() - ranges[j].size()
        def buffer = new LinkedList<>(Collections.nCopies(sizeDiff, -1))

        def tmp = ranges[i]
        ranges[i] = ranges[j]
        ranges.add(i + 1, buffer)
        ranges[j + 1] = new LinkedList<>(Collections.nCopies(tmp.size() - sizeDiff, -1))
        return true
    }
}


def part2(input) {
    def blocks = inputBlocks(input)
    //println(String.valueOf(blocks.join()).replace("-1", "."))
    def r = ranges(blocks)
    def (i, j) = [0, r.size() - 1]
    while (i < j) {
//        println("$i $j")
//        println(r)
        if (i % 100 == 0) println(i)
        if (r[i][0] != -1) {
            i++
            continue
        } else if (r[j][0] == -1) {
            j--
            continue
        }
        //println(String.valueOf(r.flatten().join()).replace("-1", "."))
        int k = i
        while (k < j) {
            if (r[k][0] == -1 && r[j][0] != -1 && r[j].size() <= r[k].size()) {
                def increaseJ = swap(r, k, j)
                if(increaseJ) j++
                break
            }
            k++
        }
        j--
    }

    checksum(r.flatten())
}

assert part2("2333133121414131402") == 2858
assert part2(inputFile.readLines()[0]) == 6413328569890