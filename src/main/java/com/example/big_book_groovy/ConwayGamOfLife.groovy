#!/usr/local/groovy

package com.example.big_book_groovy

import java.util.stream.Collectors

def height = 12
def width = 50
def numberOfGenerations = 1000
def density = 0.5
def delimiter = '-'.repeat width

def generate_field(height, width, density) {
    def random = new Random()
    (0..<height).collect { (0..<width).collect { random.nextDouble(1) > density } }
}

def generate_adjacent_coordinates(x, y) {
    [[x - 1, y + 1], [x, y + 1], [x + 1, y + 1], [x + 1, y],
     [x + 1, y - 1], [x, y - 1], [x - 1, y - 1], [x - 1, y]]
}

def calculate_next_gen(height, width, List<List<Boolean>> field) {
    (0..<height).collect { Integer rowNum ->
        (0..<width).collect { Integer columnNum ->
            def adjCoordinates = generate_adjacent_coordinates rowNum, columnNum
            def aliveCells = adjCoordinates.stream()
                    .filter { pair ->
                        def (x, y) = pair
                        // check if coordinates are in bound and cell is alive
                        x >= 0 && x < 12 && y >= 0 && y < 50 && field[x][y]
                    }.count()
            // if a cell is alive and has 2 or 3 alive adjacent cells -> it survives
            // if a cell is dead and has exactly 3 alive adjacent cells -> it revives
            // otherwise - it dies
            (field[rowNum][columnNum] && [2L, 3L].contains(aliveCells)) || (!field[rowNum][columnNum] && aliveCells == 3L)
        }
    }
}

def render_field = { List<List<Boolean>> field ->
    field.stream()
            .map { row -> row.collect { it ? '0' : ' ' }.join() }
            .collect(Collectors.joining('\n'))
}

// start of the script
def currentGen = generate_field height, width, density
def someAreAlive = currentGen.stream().flatMap { it.stream() }.anyMatch { it }

def generation = 0
while (someAreAlive && generation < numberOfGenerations) {
    def rendered = render_field currentGen
    println rendered
    println delimiter
    currentGen = calculate_next_gen height, width, currentGen
    generation++
    someAreAlive = currentGen.stream().flatMap { it.stream() }.anyMatch { it }
    Thread.sleep(500L)
}