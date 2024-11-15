#!/usr/local/groovy

package com.example.big_book_groovy

final def DND_ROLL_DICE_PATTERN = /^\d+d\d+(([+\-])\d+)?$/

def roll_dices = { rolls, size ->
    def rand = new Random()
    (0..<rolls).collect { rand.nextInt(1, size + 1) }
}

try (def reader = new BufferedReader(new InputStreamReader(System.in))) {
    println 'Please, enter how many rolls, and wha'
    def userInput = reader.readLine()
    while (!userInput.matches(DND_ROLL_DICE_PATTERN)) {
        userInput = reader.readLine()
    }
    def (rolls, size, offset) = userInput.split(/\D/).collect { it.toInteger() }
    def dices = roll_dices rolls, size
    def sum = dices.sum()
    if (offset) {
        sum += userInput.contains('-') ? -offset : offset
    }
    println "$sum: $dices"
}
