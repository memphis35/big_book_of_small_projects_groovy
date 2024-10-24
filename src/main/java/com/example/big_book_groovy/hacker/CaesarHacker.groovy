#!/usr/local/groovy

package com.example.big_book_groovy.hacker

def limits = ['LU': [65, 91], 'LL': [97, 123]]

def shiftOneLetterLeft = { Integer letter ->
    def shiftedLetter = letter as Integer
    def charLetter = letter as Character
    if (charLetter.isLetter()) {
        def limitsKey = "L${charLetter.isUpperCase() ? 'U' : 'L'}"
        def (lower, upper) = limits.get(limitsKey.toString())
        shiftedLetter--
        shiftedLetter = shiftedLetter < lower ? upper - (lower - shiftedLetter) : shiftedLetter
    }
    shiftedLetter
}

println 'Caesar Cipher Hacker, by Aleksandr Smirnov [aa.smirnov2@gmail.com]'
try (def reader = new BufferedReader(new InputStreamReader(System.in))) {
    println 'Enter the encrypted Caesar cipher message to hack.'
    print '>>> '
    def userInputEncoded = reader.readLine()
    (0..24).forEach { num ->
        def result = new StringBuilder(userInputEncoded.size())
        userInputEncoded.chars()
                .map { shiftOneLetterLeft it }
                .forEach { result.append(it as char) }
        userInputEncoded = result.toString()
        println "$num ${result.toString()}"
    }
}
