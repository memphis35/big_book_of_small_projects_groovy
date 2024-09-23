#!/usr/local/groovy
package com.example.big_book_groovy.birthday_paradox

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
    for (i in 0..<castedIterations) {
        def birthdays = new SecureRandom().ints(1, 366)
                .limit(castedDays)
                .mapToObj { LocalDate.ofYearDay(2000, it).format(DateTimeFormatter.ofPattern("d MMM")) }
                .toList()
        Set<String> birthdaySet = new HashSet<>()
        def hasDuplicate = birthdays.stream().anyMatch { !birthdaySet.add(it) }
        if (hasDuplicate) count++
    }
    def percentage = (count / castedIterations * 100).setScale(1, RoundingMode.HALF_DOWN)
    println "${count} of ${castedIterations} iterations has duplications: ${percentage}%"
}
