#!/usr/local/groovy

package com.example.big_book_groovy.caesar

def shiftRight = (Integer shiftSize, Integer letter) -> {
    def shiftedLetter = letter as Integer
    def charLetter = letter as Character
    if (charLetter.isLetter()) {
        shiftedLetter = letter + shiftSize
        def lowerLimit = charLetter.isUpperCase() ? 64 : 96
        def upperLimit = charLetter.isUpperCase() ? 90 : 122
        if (shiftedLetter > upperLimit) {
            shiftedLetter = lowerLimit + (shiftedLetter - upperLimit)
        }
    }
    shiftedLetter
}

def shiftLeft = (Integer shiftSize, Integer letter) -> {
    def shiftedLetter = letter as Integer
    def charLetter = letter as Character
    if (charLetter.isLetter()) {
        shiftedLetter = letter - shiftSize
        def lowerLimit = charLetter.isUpperCase() ? 65 : 97
        def upperLimit = charLetter.isUpperCase() ? 91 : 123
        if (shiftedLetter < lowerLimit) {
            shiftedLetter = upperLimit - (lowerLimit - shiftedLetter)
        }
    }
    shiftedLetter
}

try (def br = new BufferedReader(new InputStreamReader(System.in))) {
    println 'Caesar Cipher, by Aleksandr Smirnov [aa.smirnov2@gmail.com]'
    print 'Do you want to (e)ncrypt or (d)ecrypt? Your input is: '
    def userInputOp = br.readLine()
    while (!(userInputOp ==~ /^[de]$/)) {
        print 'Incorrect option has been chosen. Please, choose (e)ncrypt or (d)ecrypt: '
        userInputOp = br.readLine()
    }
    def action = userInputOp == 'e' ? 'encrypt' : 'decrypt'
    print 'Please, enter the key (1-25): '
    def userInputShiftSize = br.readLine()
    while (!(userInputShiftSize ==~ /^\d{1,2}$/) && !(1..25).containsWithinBounds(userInputShiftSize.toInteger())) {
        print 'Incorrect option has been chosen. Please, choose a number between 1 and 25: '
        userInputShiftSize = br.readLine()
    }
    def shiftSize = userInputShiftSize.toInteger()
    print "Enter the message to $action: "
    def userInputPhrase = br.readLine()
    while (!(userInputPhrase ==~ /^[a-zA-Z ,]+$/)) {
        print 'Plain message must contain alphabetical symbols, whitespaces and commas. Please, type your message once again: '
        userInputPhrase = br.readLine()
    }

    def builder = new StringBuilder(userInputPhrase.size())
    def shift = (userInputOp == 'e' ? shiftRight : shiftLeft).curry(shiftSize)
    userInputPhrase.chars()
            .map { shift it }
            .forEach { builder.append(it as char) }
    println builder.toString()
}