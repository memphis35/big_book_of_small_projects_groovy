#!/usr/local/groovy

package com.example.big_book_groovy.chohan

println '''Cho-Han, by Aleksandr Smirnov [aa.smirnov2@gmail.com]
In this traditional Japanese dice game, two dice are rolled in a bamboo
cup by the dealer sitting on the floor. The player must guess if the
dice total to an even (cho) or odd (han) number.
'''

def money = 5000
def random = new Random()
try (def reader = new BufferedReader(new InputStreamReader(System.in))) {
    def isGameOver = false
    while (!isGameOver) {
        println "You have $money mon. How much do you bet (100-$money)?"
        print '>>> '
        def userInputBet = reader.readLine()
        def availableBet = 100..money
        while (!userInputBet.isInteger() || !availableBet.contains(userInputBet.toInteger())) {
            println "Wrong input. Please, type an integer number between 100 and $money: "
            print '>>> '
            userInputBet = reader.readLine()
        }
        def bet = userInputBet.toInteger()
        def diceOne = random.nextInt(1, 7)
        def diceTwo = random.nextInt(1, 7)
        def sum = diceOne + diceTwo
        println """
        The dealer swirls the cup and you hear the rattle of dice. 
        The dealer slams the cup on the floor, still covering the
        dice and asks for your bet.
        CHO (even) or HAN (odd)?"""
        print '>>> '
        def userInputAnswer = reader.readLine()
        while (!userInputAnswer.matches(/(cho|han)/)) {
            println 'Wrong input. Please, choose cho (even) or han (odd): '
            print '>>> '
            userInputAnswer = reader.readLine()
        }
        def isUserChoseEven = userInputAnswer == 'cho'
        def isEven = sum % 2 == 0
        def isGuessed = (isUserChoseEven && isEven) || (!isUserChoseEven && !isEven)
        money = money + (isGuessed ? bet : bet * -1)
        println 'The dealer lifts the cup to reveal: '
        println "DICE_ONE: $diceOne, DICE_TWO: $diceTwo, SUM: $sum (${isEven ? 'even' : 'odd'})"
        println "You have ${isGuessed ? 'won' : 'lost'} and ${isGuessed ? 'gain' : 'loose'} $bet mon"
        println "Current balance is $money mon"

        if (money <= 0) {
            println 'You have lost all your money. The game is over.'
            break
        }
        println 'Would you like to play another round? (y/yes/n/no/any key): '
        print '>>> '
        def userInputConsent = reader.readLine()
        isGameOver = !(userInputConsent == 'y' || userInputConsent == 'yes')
    }
    println 'Thanks for playing and good bye!'
}
