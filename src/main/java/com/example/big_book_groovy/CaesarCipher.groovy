#!/usr/local/groovy

package com.example.big_book_groovy

def limits = ['RU': [64, 90], 'RL': [96, 122], 'LU': [65, 91], 'LL': [97, 123]]

def shift = { Integer shiftSize, Integer letter, Closure adjust ->
    def shiftedLetter = letter as Integer
    def charLetter = letter as Character
    if (charLetter.isLetter()) {
        def limitsKey = "${shiftSize > 0 ? 'R' : 'L'}${charLetter.isUpperCase() ? 'U' : 'L'}"
        def (lower, upper) = limits.get(limitsKey.toString())
        shiftedLetter = letter + shiftSize
        shiftedLetter = adjust lower, upper, shiftedLetter
    }
    shiftedLetter
}

def shiftRight = { Integer shiftSize, Integer letter ->
    shift shiftSize, letter, { low, upp, shifted -> shifted > upp ? low + (shifted - upp) : shifted }
}

def shiftLeft = { Integer shiftSize, Integer letter ->
    shift shiftSize, letter, { low, upp, shifted -> shifted < low ? upp - (low - shifted) : shifted }
}

println 'Caesar Cipher, by Aleksandr Smirnov [aa.smirnov2@gmail.com]'
try (def br = new BufferedReader(new InputStreamReader(System.in))) {
    print 'Do you want to (e)ncrypt or (d)ecrypt? Your input is: '
    def userInputOp = br.readLine()
    while (!userInputOp.isBlank() && !(userInputOp ==~ /^[de]$/)) {
        print 'Incorrect option has been chosen. Please, choose (e)ncrypt or (d)ecrypt: '
        userInputOp = br.readLine()
    }

    print 'Please, enter the key (1-25): '
    def userInputShiftSize = br.readLine()
    while (userInputShiftSize.isNumber() && !(1..25).containsWithinBounds(userInputShiftSize.toInteger())) {
        print 'Incorrect option has been chosen. Please, choose a number between 1 and 25: '
        userInputShiftSize = br.readLine()
    }
    def isEncrypt = userInputOp == 'e'
    def action = isEncrypt ? 'encrypt' : 'decrypt'
    def shiftSize = userInputShiftSize.toInteger() * (action == 'encrypt' ? 1 : -1)

    print "Enter the message to $action: "
    def userInputPhrase = br.readLine()
    while (!(userInputPhrase ==~ /^[a-zA-Z ,]+$/)) {
        print 'Plain message must contain alphabetical symbols, whitespaces and commas. Please, type your message once again: '
        userInputPhrase = br.readLine()
    }

    def builder = new StringBuilder(userInputPhrase.size())
    def doShift = (isEncrypt ? shiftRight : shiftLeft).curry(shiftSize)
    userInputPhrase.chars()
            .map { doShift it }
            .forEach { builder.append(it as char) }
    println builder.toString()
}