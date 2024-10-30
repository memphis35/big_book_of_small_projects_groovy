#!/usr/local/groovy

package com.example.big_book_groovy

println 'Diamonds, by Aleksandr Smirnov [aa.smirnov2@gmail.com]'

def drawFilledLines = { Integer size, IntRange range, List<String> slashes ->
    def (lSlash, rSlash) = slashes
    range.collect { (' ' * (size - it)) + (lSlash * it) + (rSlash * it) + (' ' * (size - it)) }.join('\n')
}

def drawFilledDiamond = { Integer size ->
    def topLines = drawFilledLines size, (1..size), ['/', '\\']
    def bottomLines = drawFilledLines size, (size..1), ['\\', '/']
    "$topLines\n$bottomLines"
}

def drawOutlinedLines = { Integer size, IntRange range, List<String> slashes ->
    def (lSlash, rSlash) = slashes
    range.collect { (' ' * (size - it)) + lSlash + (' ' * ((it - 1) * 2)) + rSlash + (' ' * (size - it))}.join('\n')
}

def drawOutlinedDiamond = { Integer size ->
    def topLines = drawOutlinedLines size, (1..size), ['/', '\\']
    def bottomLines = drawOutlinedLines size, (size..1), ['\\', '/']
    "$topLines\n$bottomLines"
}

try (def reader = new BufferedReader(new InputStreamReader(System.in))) {
    print 'Enter the form (O)utlined or (F)illed, and size (1 - 99): \n>>> '
    def userInput = reader.readLine()
    while (!(userInput ==~ /^[OF][1-9][0-9]?$/)) {
        print 'Wrong input. Please, enter (O)utlined or (F)illed, and size (1 - 9), f.e. D3, F7.\n>>> '
        userInput = reader.readLine()
    }

    def diamond = switch (userInput[0]) {
        case 'O' -> drawOutlinedDiamond userInput.substring(1).toInteger()
        case 'F' -> drawFilledDiamond userInput.substring(1).toInteger()
        default -> throw new IllegalArgumentException("Invalid argument: ${userInput[0]}")
    }
    println diamond
}
