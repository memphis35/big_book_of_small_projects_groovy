#!/usr/local/groovy
package com.example.big_book_groovy.birthday

import java.math.RoundingMode
import java.security.SecureRandom
import java.time.LocalDate
import java.time.format.DateTimeFormatter

try (def reader = new BufferedReader(new InputStreamReader(System.in))) {
    print 'How many birthdays shall I generate? (max 100): '
    def userInputDays = reader.readLine()
    def castedDays = userInputDays.isNumber() ? userInputDays.toInteger() : 23
    print 'How many iterations shall I generate? (max 1 000 000): '
    def userInputIterations = reader.readLine()
    def castedIterations = userInputIterations.isNumber() ? userInputIterations.toInteger() : 23
    def count = 0
    (1..castedIterations).forEach {
        def birthdays = generate_birthdays castedDays
        def birthdaySet = new HashSet<String>()
        def hasDuplicate = birthdays.stream().anyMatch { !birthdaySet.add(it) }
        if (hasDuplicate) count++
    }
    def percentage = calculate_percentage count, castedIterations
    println "${count} of ${castedIterations} iterations has duplications: ${percentage}%"
}

def calculate_percentage(count, iterations) {
    (count / iterations * 100).setScale 1, RoundingMode.HALF_DOWN
}

def generate_birthdays(Integer count) {
    def formatter = DateTimeFormatter.ofPattern 'd MMM'
    new SecureRandom().ints(1, 366)
            .limit(count)
            .mapToObj { LocalDate.ofYearDay 2000, it }
            .map {formatter.format it }
            .toList()
}