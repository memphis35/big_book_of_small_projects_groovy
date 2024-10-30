#!/usr/local/groovy

package com.example.big_book_groovy

def collatzSequence = { num -> num % 2 == 0 ? num / 2 : 3 * num + 1 }

// script start
println 'Collatz Sequence, or, the 3n + 1 Problem, by Aleksandr Smirnov [aa.smirnov2@gmail.com]'

println '''The Collatz sequence is a sequence of numbers produced from a starting 
number n, following three rules:
    1) If n is even, the next number n is n / 2.
    2) If n is odd, the next number n is n * 3 + 1.
    3) If n is 1, stop. Otherwise, repeat.
It is generally thought, but so far not mathematically proven, that every starting number eventually terminates at 1.
'''

try (def reader = new BufferedReader(new InputStreamReader(System.in))) {
    print 'Enter a starting number (greater than 0):\n>>> '
    def userInputNumber = reader.readLine()
    while (!userInputNumber.isBigInteger() || (userInputNumber.toBigInteger() <=> BigInteger.ZERO) <= 0) {
        print 'Wrong input. Please, enter a positive integer number:\n>>> '
        userInputNumber = reader.readLine()
    }
    def number = userInputNumber.toBigInteger()

    while (number != 1) {
        print "$number -> "
        number = collatzSequence number
    }
    println number
}
