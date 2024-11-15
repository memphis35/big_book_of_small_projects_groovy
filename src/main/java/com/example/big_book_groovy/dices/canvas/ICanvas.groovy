package com.example.big_book_groovy.dices.canvas

import com.example.big_book_groovy.dices.d6.IDice

interface ICanvas {

    def drawDice(IDice dice)

    def print()
}