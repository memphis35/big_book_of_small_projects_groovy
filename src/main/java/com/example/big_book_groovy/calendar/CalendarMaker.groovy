#!/usr/local/groovy

package com.example.big_book_groovy.calendar

import java.time.LocalDate
import java.time.Month

final def daysOfWeek = "   MON     TUE     WED     THU     FRI     SAT     SUN   "
final def delimiter = "+-------+-------+-------+-------+-------+-------+-------+"
final def emptyLine = "|       |       |       |       |       |       |       |"

def printHeader = { Integer year, Integer month ->
    def heading = "${Month.of(month)} $year"
    def adjustments = (57 - heading.size()).toInteger()
    def leftAdjustment = adjustments / 2 - (adjustments % 2 == 0 ? 0 : 1)
    def leftWhiteSpaces = '-'.repeat(leftAdjustment.intValue() - 1)
    def rightAdjustment = adjustments - leftAdjustment
    def rightWhiteSpaces = '-'.repeat(rightAdjustment.intValue() - 1)
    println "$leftWhiteSpaces $heading $rightWhiteSpaces"
    println daysOfWeek
}

def formatDayCell = { day -> day.dayOfMonth < 10 ? "| $day.dayOfMonth     " : "|$day.dayOfMonth     " }

def printWeek = { List<LocalDate> week ->
    def builder = new StringBuilder(57)
    week.stream().map { day -> formatDayCell day }.forEach { builder.append it }
    builder.append('|')
    println builder.toString()
}

println 'Calendar Maker, by Aleksandr Smirnov, [aa.smirnov2@gmail.com]'
try (def reader = new BufferedReader(new InputStreamReader(System.in))) {
    print 'Enter the year for the calendar (0-9999): '
    def userInputYear = reader.readLine()
    def year = userInputYear.toInteger()

    print 'Enter the month for the calendar (1-12): '
    def userInputMonth = reader.readLine()
    def month = userInputMonth.toInteger()

    def startDay = LocalDate.of year, month, 1
    def endDay = startDay.plusMonths(1).minusDays(1)

    def startAdjustment = startDay.dayOfWeek.value - 1
    def endAdjustment = 7 - endDay.dayOfWeek.value

    def firstDay = startAdjustment == 0 ? startDay : startDay.minusDays(startAdjustment)
    def lastDay = endAdjustment == 0 ? endDay : endDay.plusDays(endAdjustment)

    printHeader year, month
    def currentWeek = []
    for (day in firstDay..lastDay) {
        currentWeek << day
        if (currentWeek.size() == 7) {
            println delimiter
            printWeek currentWeek
            println emptyLine
            currentWeek.clear()
        }
    }
    println delimiter
}