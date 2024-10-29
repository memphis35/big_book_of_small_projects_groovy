#!/usr/local/groovy

package com.example.big_book_groovy

final def FIRST_ROW = ["0": "*****", "1": "    *", "2": "*****", "3": "*****", "4": "*   *",
                       "5": "*****", "6": "*****", "7": "*****", "8": "*****", "9": "*****", "D": "       "]
final def SECOND_ROW = ["0": "*   *", "1": "    *", "2": "    *", "3": "    *", "4": "*   *",
                        "5": "*    ", "6": "*    ", "7": "    *", "8": "*   *", "9": "*   *", "D": "   0   "]
final def THIRD_ROW = ["0": "*   *", "1": "    *", "2": "*****", "3": " ****", "4": "*****",
                       "5": "*****", "6": "*****", "7": "   * ", "8": "*****", "9": "*****", "D": "       "]
final def FOURTH_ROW = ["0": "*   *", "1": "    *", "2": "*    ", "3": "    *", "4": "    *",
                        "5": "    *", "6": "*   *", "7": "  *  ", "8": "*   *", "9": "    *", "D": "   0   "]
final def FIFTH_ROW = ["0": "*****", "1": "    *", "2": "*****", "3": "*****", "4": "    *",
                       "5": "*****", "6": "*****", "7": " *   ", "8": "*****", "9": "*****", "D": "       "]
final def ROWS = [FIRST_ROW, SECOND_ROW, THIRD_ROW, FOURTH_ROW, FIFTH_ROW]

def tick = { List<Integer> clock ->
    def newClock = []
    if (clock[0] > 0) {
        newClock = [clock[0] - 1, clock[1], clock[2]]
    } else if (clock[0] == 0 && clock[1] > 0) {
        newClock = [59, clock[1] - 1, clock[2]]
    } else if (clock[0] == 0 && clock[1] == 0 && clock[2] > 0) {
        newClock = [59, 59, clock[2] - 1]
    }
    newClock
}

def parseClock = { List<Integer> clock ->
    [(clock[2] < 10 ? '0' : '') + clock[2].toString(), "D",
     (clock[1] < 10 ? '0' : '') + clock[1].toString(), "D",
     (clock[0] < 10 ? '0' : '') + clock[0].toString()].join()
}

def renderClock = { String clock ->
    ROWS.collect { row -> (0..<clock.size()).collect { row.get(clock[it]) }.join('  ') }.join('\n')
}

try (def reader = new BufferedReader(new InputStreamReader(System.in))) {
    println 'Countdown timer, by Aleksandr Smirnov [aa,smirnov2@gmail.com]'
    print 'Enter desirable period in seconds: \n >>> '
    def userInputSeconds = reader.readLine()
    while (!userInputSeconds.isInteger() || userInputSeconds.toInteger() <= 0) {
        print 'Wrong input. Enter a positive integer value: \n >>> '
        userInputSeconds = reader.readLine()
    }
    def totalInSeconds = userInputSeconds.toInteger()

    def seconds = (totalInSeconds.intdiv(60 ** 0)) % 60
    def minutes = (totalInSeconds.intdiv(60 ** 1)) % 60
    def hours = (totalInSeconds.intdiv(60 ** 2)) % 60
    def clock = [seconds, minutes, hours]
    while (!clock.isEmpty()) {
        def parsedClock = parseClock clock
        def renderedClock = renderClock parsedClock
        println renderedClock
        clock = tick clock
        Thread.sleep(1000L)
    }
}