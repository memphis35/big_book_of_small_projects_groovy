#!/usr/local/groovy
package com.example.big_book_groovy.carrot

def random = new Random()

def normalizeName = { String name ->
    name.trim().toLowerCase().capitalize()
}
def adjustName = { String name ->
    if (name.size() > 6) {
        name = name.substring 0, 6
    } else if (name.size() < 6) {
        name = name + ' '.repeat(6 - name.size())
    }
    name
}

def showClosedBoxes = { p1, p2 ->
    println """
        |------|     |------|
        | GOLD |     | BLUE |
        |------|     |------|
        [$p1]     [$p2]
"""
}

def showOpenedGoldBoxWithCarrot = { p1, p2 -> println """
          VVVV
       \\   VV   /
        |  ||  |     |------|
        | GOLD |     | BLUE |
        |------|     |------|
        (carrot)
        [$p1]     [$p2]
"""
}

def showOpenedGoldBoxWithoutCarrot = { p1, p2 -> println """
       \\        /
        |      |     |------|
        | GOLD |     | BLUE |
        |------|     |------|
        (empty)
        [$p1]     [$p2]
"""
}

def doShowdownGoldBoxWins = { p1, p2 -> println """
          VVVV
       \\   VV   /   \\        /
        |  ||  |     |      |
        | GOLD |     | BLUE |
        |------|     |------|
        (carrot)     (empty)
        [$p1]     [$p2]
"""
}

def doShowdownBlueBoxWins = { p1, p2 -> println """
                       VVVV
       \\        /   \\   VV   /
        |      |     |  ||  |
        | GOLD |     | BLUE |
        |------|     |------|
        (empty)      (carrot)
        [$p1]     [$p2]
"""
}

println 'Carrot in a Box, by Aleksandr Smirnov [aa.smirnov2@gmail.com]'

try (def reader = new BufferedReader(new InputStreamReader(System.in))) {
    print 'Player 1, enter your name: '
    def playerOne = normalizeName reader.readLine()
    def playerOneAdj = adjustName playerOne
    print 'Player 2, enter your name: '
    def playerTwo = normalizeName reader.readLine()
    def playerTwoAdj = adjustName playerTwo

    println 'HERE ARE TWO BOXES:'
    showClosedBoxes playerOneAdj, playerTwoAdj
    println "$playerOne, you have a GOLD box in front of you"
    println "$playerTwo, you have a BLUE box in front of you"
    println "$playerTwo, close your eyes. After that, $playerOne, press \"Enter\" to continue"
    reader.readLine()

    println "$playerOne, here is what is inside your box: "
    def isCarrotInGoldBox = random.nextBoolean()
    if (isCarrotInGoldBox) {
        showOpenedGoldBoxWithCarrot playerOneAdj, playerTwoAdj
    } else {
        showOpenedGoldBoxWithoutCarrot playerOneAdj, playerTwoAdj
    }
    println "$playerOne, press \"Enter\" when you are ready to continue"
    reader.readLine()
    print(System.lineSeparator() * 50)

    println "$playerTwo, you now may open your eyes"
    println "$playerOne, say one of the following sentences: "
    println '   (1) There IS a carrot in my box'
    println '   (2) There IS NOT a carrot in my box'
    println 'Press "Enter" to continue...'
    reader.readLine()

    print "$playerTwo, do you want to swap boxes with $playerOne? (y/n): "
    def playerTwoInputAnswer = reader.readLine()
    while (playerTwoInputAnswer ==~ /[^yn]/) {
        print 'Wrong input. Please, choose (y)es or (n)o: '
        playerTwoInputAnswer = reader.readLine()
    }
    isCarrotInGoldBox = playerTwoInputAnswer == 'y' ? !isCarrotInGoldBox : isCarrotInGoldBox
    def winner = isCarrotInGoldBox ? playerOne : playerTwo
    if (isCarrotInGoldBox) {
        doShowdownGoldBoxWins playerOneAdj, playerTwoAdj
    } else {
        doShowdownBlueBoxWins playerOneAdj, playerTwoAdj
    }
    println "$winner is the winner!"
}
