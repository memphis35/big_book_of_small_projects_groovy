package com.example.big_book_groovy.dices.d6

class Dice implements IDice {

    static List<Dice> getDices() {
        [new D1Dice(), new D2aDice(), new D2bDice(), new D3aDice(), new D3bDice(), new D4Dice(), new D5Dice(), new D6aDice(), new D6bDice()]
    }

    public final List<String> lines
    public final Integer value

    protected Dice(List<String> lines, Integer value) {
        this.lines = Collections.unmodifiableList(lines)
        this.value = value
    }
}
