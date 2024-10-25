#!/usr/local/groovy
package com.example.big_book_groovy.carrot

def random = new Random()

def adjustName = { String name ->
    if (name.size() > 6) {
        name = name.substring 0, 6
    } else if (name.size() < 6) {
        name = name + ' '.repeat(6 - name.size())
    }
    name.capitalize()
}

println 'Carrot in a Box, by Aleksandr Smirnov [aa.smirnov2@gmail.com]'

try (def reader = new BufferedReader(new InputStreamReader(System.in))) {
    print 'Player 1, enter your name: '
    def playerOne = reader.readLine().trim()
    def playerOneAdj = adjustName playerOne
    print 'Player 2, enter your name: '
    def playerTwo = reader.readLine().trim()
    def playerTwoAdj = adjustName playerTwo
    def closedBoxes = """\
        |------|     |------|
        | GOLD |     | BLUE |
        |------|     |------|
        [$playerOneAdj]     [$playerTwoAdj]
"""
    println 'HERE ARE TWO BOXES:'
    println closedBoxes
    println "$playerOne, you have a GOLD box in front of you"
    println "$playerTwo, you have a BLUE box in front of you"
    println "$playerTwo, close your eyes. After that, $playerOne, press \"Enter\" to continue"
    reader.readLine()

    def isCarrotInGoldBox = random.nextBoolean()

    println "$playerOne, here is what is inside your box: "
    def openedGoldBoxWithCarrot = """\
          VVVV
       \\   VV   /
        |  ||  |     |------|
        | GOLD |     | BLUE |
        |------|     |------|
        (carrot)
        [$playerOneAdj]     [$playerTwoAdj]
"""
    def openedGoldBoxWithoutCarrot = """\
       \\        /
        |      |     |------|
        | GOLD |     | BLUE |
        |------|     |------|
        (empty)
        [$playerOneAdj]     [$playerTwoAdj]
"""
    println isCarrotInGoldBox ? openedGoldBoxWithCarrot : openedGoldBoxWithoutCarrot
    println "$playerOne, press \"Enter\" when you are ready to continue"
    reader.readLine()
    print(System.lineSeparator() * 50)
    println "$playerTwo, you now may open your eyes"

    print """$playerOne, say one of the following sentences: 
    (1) There IS a carrot in my box
    (2) There IS NOT a carrot in my box
Press \"Enter\" to continue...
"""
    reader.readLine()
    print "$playerTwo, do you want to swap boxes with $playerOne? (y/n): "
    def playerTwoInputAnswer = reader.readLine() //TODO validate
    isCarrotInGoldBox = playerTwoInputAnswer == 'y' ? !isCarrotInGoldBox : isCarrotInGoldBox
    def openedBoxesWithCarrotInGold = """\
          VVVV
       \\   VV   /   \\        /
        |  ||  |     |      |
        | GOLD |     | BLUE |
        |------|     |------|
        (carrot)     (empty)
        [$playerOneAdj]     [$playerTwoAdj]
"""
    def openedBoxesWithCarrotInBlue = """\
                       VVVV
       \\        /   \\   VV   /
        |      |     |  ||  |
        | GOLD |     | BLUE |
        |------|     |------|
        (empty)      (carrot)
        [$playerOneAdj]     [$playerTwoAdj]
"""
    println isCarrotInGoldBox ? openedBoxesWithCarrotInGold : openedBoxesWithCarrotInBlue
    println "${isCarrotInGoldBox ? playerOne : playerTwo} is the winner!"
}
