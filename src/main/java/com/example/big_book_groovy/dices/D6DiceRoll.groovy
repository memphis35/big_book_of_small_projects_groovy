package com.example.big_book_groovy.dices

import com.example.big_book_groovy.dices.d6.Dice

class D6DiceRoll implements DiceRoll {

    private final RAND = new Random()
    private List<Dice> dices = Dice.getDices()

    @Override
    Dice roll() {
        return dices[RAND.nextInt(dices.size())]
    }
}
