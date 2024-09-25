#!/usr/local/groovy
package com.example.big_book_groovy.bagel

import java.security.SecureRandom
import java.util.stream.Collectors

/* execution part */
println '''Bagel game greets you, traveler!
        These are rules: 
        1. A secret number contains 3 (three) distinctive digits
        2. After you input your guess it shows you one of the following answers:
            2.1 “Pico” when your guess has a correct digit in the wrong place
            2.2 “Fermi” when your guess has a correct digit in the correct place
            2.3 “Bagels” if your guess has no correct digits
        3. You have 10 tries to guess the secret number'''

try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
    def input = 'y'
    while (input == 'y') {
        def result = start_game reader
        println "The game is over and you have ${result ? 'won' : 'lost'}"
        print 'Do you want to play one more time? (y/n/any_key): '
        input = reader.readLine()
    }
}

def start_game(bReader) {
    def count = 1
    def isGuessed = false
    def secret = generate_secret()
    while (!isGuessed && count < 11) {
        print "Guess #${count++}: "
        def userGuess = bReader.readLine()
        def answer = check_guess secret, userGuess
        println answer.stream().collect(Collectors.joining(" "))
        isGuessed = answer.size() == 3 && answer.stream().allMatch { it == 'Fermi' }
    }
    isGuessed
}

def generate_secret() {
    new SecureRandom().ints(0, 10)
            .limit(20)
            .distinct()
            .limit(3)
            .mapToObj { it.toString() }
            .collect(Collectors.joining())
}

def check_guess(secret, userGuess) {
    assert userGuess =~ /\d{3}/
    def result = (0..2).stream()
            .map { find_match secret, userGuess, it }
            .filter { Objects.nonNull it }
            .toList() as List<String>
    result.isEmpty() ? ['Bagel'] : result
}

def find_match(secret, input, index) {
    def currentChar = input.charAt(index)
    def result = secret.charAt(index) == currentChar ? 'Fermi' : null
    if (!result) result = secret.indexOf(currentChar as Integer) >= 0 ? 'Pico' : null
    result
}