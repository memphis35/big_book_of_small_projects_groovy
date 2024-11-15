package com.example.big_book_groovy.dices

import com.example.big_book_groovy.dices.canvas.ICanvasImpl

class DiceGame {

    public static def MIN_DICES = 2
    public static def MAX_DICES = 6

    static void main(String[] args) {
        try (def reader = new BufferedReader(new InputStreamReader(System.in))) {
            def one_more_play = true
            while (one_more_play) {
                def canvas = new ICanvasImpl()
                def roll = new D6DiceRoll()

                def dicesCount = new Random().nextInt(MIN_DICES, MAX_DICES + 1)
                def sum = 0
                for (i in (1..dicesCount)) {
                    def dice = roll.roll()
                    canvas.drawDice dice
                    sum += dice.value
                }

                canvas.print()

                print 'Your answer is: '
                def userInput = reader.readLine()
                if (userInput.isInteger() && sum == userInput.toInteger()) {
                    println 'Right answer!'
                } else {
                    println 'Wrong answer!'
                }
                print 'Wanna play another round? (yes/y/no/n/any): '
                def userAnswer = reader.readLine()
                one_more_play = userAnswer.matches(/^(yes|y)$/)
            }
        }

    }
}
