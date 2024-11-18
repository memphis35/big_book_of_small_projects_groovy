#!/usr/local/groovy

package com.example.big_book_groovy

def final DENSITY = 0.18
def final STREAM_SIZE_MIN = 6
def final STREAM_SIZE_MAX = 14
def final SYMBOLS = "0123456789"
def final LINE = (0..<135).collect { [] }

def rand = new Random()

while (true) {
    // calculate density
    def empties = LINE.count([])
    def current_density = 1 - (empties / LINE.size())
    while (current_density < DENSITY) {
        // add a stream
        def empty_indexes = (0..<LINE.size()).findAll { !LINE[it] }
        def next_random_index = empty_indexes[rand.nextInt(empty_indexes.size())]
        def stream_size = rand.nextInt(STREAM_SIZE_MIN, STREAM_SIZE_MAX + 1)
        LINE[next_random_index] = (0..stream_size).collect { SYMBOLS[rand.nextInt(SYMBOLS.size())]}
        --empties
        current_density = 1 - (empties / LINE.size())
    }
    // print line
    def toPrint = new StringBuilder(100)
    LINE.forEach { list ->
        def symbol = list.isEmpty() ? ' ' : list.pop()
        toPrint << symbol << ' '
    }
    println toPrint
    Thread.sleep(100L)
}
