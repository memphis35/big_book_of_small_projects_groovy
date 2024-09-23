package com.example.big_book_groovy.bagel

import java.security.SecureRandom
import java.util.stream.Collectors

class BagelGame {

    private String secretNumber

    def start(bReader) {
        secretNumber = generate()
        def count = 1
        while (count < 11) {
            print "Enter your guess here (${count++}): "
            def userInput = bReader.readLine()
            def answer = this.check(userInput)
            println answer.stream().collect(Collectors.joining(" "))
            if (answer.size() == 3 && answer.stream().allMatch { it == 'Fermi' }) {
                return 0
            }
        }
        return 1
    }

    private static def generate() {
        new SecureRandom().ints(0, 10)
                .limit(20)
                .distinct()
                .limit(3)
                .mapToObj { it.toString() }
                .collect(Collectors.joining())
    }

    List<String> check(String input) {
        assert input =~ /\d{3}/
        def result = (0..2).stream()
                .map { index ->
                    {
                        def currentChar = input.charAt(index)
                        def result = secretNumber.charAt(index) == currentChar ? 'Fermi' : null
                        if (!result) result = secretNumber.indexOf((int) currentChar) >= 0 ? 'Pico' : null
                        result
                    }
                }
                .filter { Objects.nonNull(it) }
                .toList() as List<String>
        result.isEmpty() ? ['Bagel'] : result
    }
}
