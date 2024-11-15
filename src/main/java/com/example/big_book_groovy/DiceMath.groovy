#!/usr/local/groovy

package com.example.big_book_groovy

// variables
def final CANVAS_WIDTH = 70
def final CANVAS_HEIGHT = 12
def final MIN_DICES = 2
def final MAX_DICES = 6
def final RAND = new Random()

def d1a = ['+-----+', '|     |', '|  O  |', '|     |', '+-----+']
def d2a = ['+-----+', '| O   |', '|     |', '|   O |', '+-----+']
def d2b = ['+-----+', '|   O |', '|     |', '| O   |', '+-----+']
def d3a = ['+-----+', '| O   |', '|  O  |', '|   O |', '+-----+']
def d3b = ['+-----+', '|   O |', '|  O  |', '| O   |', '+-----+']
def d4a = ['+-----+', '| O O |', '|     |', '| O O |', '+-----+']
def d5a = ['+-----+', '| O O |', '|  O  |', '| O O |', '+-----+']
def d6a = ['+-----+', '| OOO |', '|     |', '| OOO |', '+-----+']
def d6b = ['+-----+', '| O O |', '| O O |', '| O O |', '+-----+']
def dices = [[1, d1a], [2, d2a], [2, d2b], [3, d3a], [3, d3b], [4, d4a], [5, d5a], [6, d6a], [6, d6b]]

// functions
def generate_canvas = { ->
    (0..CANVAS_HEIGHT).collect { ' ' * CANVAS_WIDTH << '\n' }
}

def get_random_coordinates = { ->
    def x = RAND.nextInt(CANVAS_HEIGHT - 7)
    def y = RAND.nextInt(CANVAS_WIDTH - 7)
    [x, y]
}

def pick_random_dice = { -> dices[RAND.nextInt(dices.size())] }

def check_canvas_space = { List<StringBuffer> canvas, x, y -> canvas[x..<(x + 7)].every { it[y..<(y + 7)].isBlank() } }


// script start
try (def reader = new BufferedReader(new InputStreamReader(System.in))) {
    def one_more_play = true
    while (one_more_play) {
        def canvas = generate_canvas()
        def dicesCount = RAND.nextInt(MIN_DICES, MAX_DICES + 1)
        def sum = 0
        for (i in (1..dicesCount)) {
            def (x, y) = get_random_coordinates()
            while (!check_canvas_space(canvas, x, y)) {
                (x, y) = get_random_coordinates()
            }
            def (Integer val, List<String> dice) = pick_random_dice()
            def index = 0
            dice.forEach { line -> canvas[x + index++][y..<(y + 7)] = line }
            sum += val
        }

        canvas.forEach { print it }

        print 'Your answer is: '
        def userInput = reader.readLine()
        if (userInput.isInteger() && sum == userInput.toInteger()) {
            println 'Right answer!'
        } else {
            println 'Wrong answer!'
        }
        print 'Wanna play another round? (yes/y/no/n/any): '
        def userAnswer = reader.readLine()
        one_more_play = userAnswer.matches(/^(yes|y)$/)
    }
}
