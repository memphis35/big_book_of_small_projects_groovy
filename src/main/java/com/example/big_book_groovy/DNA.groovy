#!/usr/local/groovy

package com.example.big_book_groovy

def final RAND = new Random()
def acids = ['A', 'G', 'C', 'T', 'U']
def lines = [
        "      #%s-%s#",
        "     #%s---%s#",
        "    #%s-----%s#",
        "   #%s------%s#",
        "  #%s------%s#",
        "  #%s-----%s#",
        "   #%s---%s#",
        "    #%s-%s#",
        "     ##",
        "    #%s-%s#",
        "   #%s---%s#",
        "  #%s-----%s#",
        "  #%s------%s#",
        "   #%s------%s#",
        "    #%s-----%s#",
        "     #%s---%s#",
        "      #%s-%s#",
        "       ##"]

def get_complement_acid = { acid ->
    switch (acid) {
        case 'A' -> 'T'
        case 'T' -> 'A'
        case 'C' -> 'G'
        case 'U' -> 'G'
        case 'G' -> RAND.nextInt(2) == 0 ? 'C' : 'U'
        default -> throw new IllegalArgumentException()
    }
}

// script start
println '''\
 --- DNA Visualization, by Aleksandr Smirnov [aa.smirnov2@gmail.com]
 A simple animation of a DNA double-helix. Press Ctrl-C to stop.
 Inspired by matoken https://asciinema.org/a/155441
 Tags: short, artistic, scrolling, science
'''
while (true) {
    lines.forEach { line ->
        def acid = acids[RAND.nextInt(acids.size())]
        def compAcid = get_complement_acid acid
        println line.formatted(acid, compAcid)
        Thread.sleep(100L)
    }
}