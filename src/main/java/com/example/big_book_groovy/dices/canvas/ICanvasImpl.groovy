package com.example.big_book_groovy.dices.canvas

import com.example.big_book_groovy.dices.d6.IDice

class ICanvasImpl implements ICanvas {

    private static final def CANVAS_WIDTH = 70
    private static final def CANVAS_HEIGHT = 12
    private final def RAND = new Random()
    private final List<StringBuffer> canvas

    ICanvasImpl() {
        canvas = (0..CANVAS_HEIGHT).collect { ' ' * CANVAS_WIDTH << '\n' }
    }

    @Override
    def drawDice(IDice dice) {
        def x = RAND.nextInt(CANVAS_HEIGHT - 7)
        def y = RAND.nextInt(CANVAS_WIDTH - 7)
        while (!canvas[x..<(x + 7)].every { it[y..<(y + 7)].isBlank() }) {
            x = RAND.nextInt(CANVAS_HEIGHT - 7)
            y = RAND.nextInt(CANVAS_WIDTH - 7)
        }
        def index = 0
        dice.lines.forEach { line -> canvas[x + index++][y..<(y + 7)] = line }
    }

    @Override
    def print() {
        this.canvas.forEach { print it }
    }
}
