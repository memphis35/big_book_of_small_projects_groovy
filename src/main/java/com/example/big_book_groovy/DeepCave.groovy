#!/usr/local/groovy

package com.example.big_book_groovy

println 'Deep Cave animation, by Aleksandr Smirnov [aa.smirnov2@gmail.com]'

def width = 30
def holeSize = 10
def shift = 0
def delay = 500L
def aligns = { ->
    def x = (width - holeSize)
    def left = (x / 2) + (x % 2) + shift
    [left, width - left - holeSize]
}

def random = new Random()

while (true) {
    def (leftAlign, rightAlign) = aligns()
    def line = ('#' * leftAlign) + (' ' * holeSize) + ('#' * rightAlign)
    println line
    shift += random.nextInt(-1, 2)
    Thread.sleep(delay)
    if ([leftAlign, rightAlign].any { it == 0 }) {
        break
    }
}

