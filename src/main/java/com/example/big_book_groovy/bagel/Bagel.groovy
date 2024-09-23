#!/usr/local/groovy
package com.example.big_book_groovy.bagel

println '''Bagel game greets you, traveler!
        These are rules: 
        1. A secret number contains 3 (three) distinctive digits
        2. After you input your guess it shows you one of the following answers:
            2.1 “Pico” when your guess has a correct digit in the wrong place
            2.2 “Fermi” when your guess has a correct digit in the correct place
            2.3 “Bagels” if your guess has no correct digits
        3. You have 10 tries to guess the secret number'''
def game = new BagelGame()
def input = 'y'
try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
    while (input == 'y') {
        def result = game.start(reader)
        println "Game over and you ${result == 0 ? 'won' : 'lost'}"
        print 'Game over. Wanna play one more time? (y/any_key): '
        input = reader.readLine()
    }
}

