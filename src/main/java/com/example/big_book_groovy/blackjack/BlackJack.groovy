#!/usr/local/groovy

package com.example.big_book_groovy.blackjack

import java.util.stream.Collectors

def playerMoney = 200
println '''
Blackjack game

Rules:
Try to get as close to 21 without going over.
Kings, Queens, and Jacks are worth 10 points.
Aces are worth 1 or 11 points.
Cards 2 through 10 are worth their face value.
    - (H)it to take another card.
    - (S)tand to stop taking cards.
On your first play, you can (D)ouble down to increase your bet
but must hit exactly one more time before standing.
In case of a tie, the bet is returned to the player.
The dealer stops hitting at 17.

Money: %d$
'''.formatted(playerMoney, playerMoney)
def roundDelimiter = '-'.repeat(30)

def suites = ['♣', '♦', '♥', '♠']
def nominals = ['2': 2, '3': 3, '4': 4, '5': 5, '6': 6, '7': 7, '8': 8, '9': 9, '10': 10, 'J': 10, 'Q': 10, 'K': 10, 'A': 0]

try (def reader = new BufferedReader(new InputStreamReader(System.in))) {
    // game start
    def isGameOver = false
    while (!isGameOver) {
        println "Current balance is $playerMoney"
        println roundDelimiter
        println 'Shuffling a deck... '
        def deck = generate_shuffled_deck suites, nominals
        println 'Dealing cards... '
        def (playerHand, dealerHand) = deal_cards deck
        show_hands_secured playerHand, dealerHand

        // offer a bet
        def bet = offer_bet reader, playerMoney

        // start a round
        def isPlayerTurnAvailable = true
        def isDealerTurnAvaliable = true
        while (isPlayerTurnAvailable || isDealerTurnAvaliable) {
            if (isPlayerTurnAvailable) {
                def action = offer_action reader
                bet += action == 'Double' ? bet : 0
                isPlayerTurnAvailable = do_player_turn action, playerHand, deck
                def isHandBusted = calculate_hand_score(playerHand) > 21
                if (isHandBusted) {
                    break
                }
            }
            if (isDealerTurnAvaliable) {
                isDealerTurnAvaliable = do_dealer_turn dealerHand, deck
            }
            show_hands_secured playerHand, dealerHand
        }
        // showdown
        def (pScore, dScore) = show_hands_disclosed playerHand, dealerHand
        if (pScore > 21 || (dScore <= 21 && dScore > pScore)) {
            println "$pScore vs $dScore. Dealer wins"
            playerMoney -= bet
        } else if (dScore > 21 || (pScore > dScore)) {
            println "$pScore vs $dScore. Player wins"
            playerMoney += (bet * 2)
        } else {
            println "$pScore vs $dScore. Nobody wins"
        }
        println roundDelimiter
        isGameOver = playerMoney <= 0
    }
}

def generate_shuffled_deck(List<String> suites, Map<String, Integer> nominals) {
    def deck = new Stack<>()
    suites.stream()
            .flatMap { suit -> nominals.entrySet().stream().map { nominal -> new Tuple2(nominal, suit) } }
            .forEach { deck.push it }
    deck.shuffle()
    deck
}

def deal_cards(Stack deck) {
    def playerHand = []
    def dealerHand = []
    playerHand << deck.pop() << deck.pop()
    dealerHand << deck.pop() << deck.pop()
    [playerHand, dealerHand]
}

def show_hands_secured(playerHand, dealerHand) {
    def pHand = playerHand.stream()
            .map { "${it.getFirst().getKey()}${it.getSecond()}" }
            .collect(Collectors.joining(' '))
    def dHand = dealerHand.stream()
            .skip(1)
            .map { "${it.getFirst().getKey()}${it.getSecond()}" }
            .collect(Collectors.joining(' '))
    def pHandScore = calculate_hand_score playerHand
    println "Player hand $pHand ($pHandScore) vs Dealer's hand ?? $dHand (unavailable)"
}

def show_hands_disclosed(playerHand, dealerHand) {
    def pHand = playerHand.stream()
            .map { "${it.getFirst().getKey()}${it.getSecond()}" }
            .collect(Collectors.joining(' '))
    def dHand = dealerHand.stream()
            .map { "${it.getFirst().getKey()}${it.getSecond()}" }
            .collect(Collectors.joining(' '))
    def pHandScore = calculate_hand_score playerHand
    def dHandScore = calculate_hand_score dealerHand
    println "Player hand $pHand ($pHandScore) vs Dealer's hand $dHand ($dHandScore)"
    [pHandScore, dHandScore]
}

def calculate_hand_score(hand) {
    def sum = hand.stream().mapToInt { it.getFirst().getValue() }.sum()
    def hasAce = hand.stream().anyMatch { card -> card.getFirst().getKey() == 'A' }
    if (hasAce) {
        sum += sum < 11 ? 11 : 1
    }
    sum
}

def offer_bet(BufferedReader reader, Integer limit) {
    print "Enter your bet (1-$limit): "
    def userBetInput = reader.readLine()
    while (!(bet_is_valid_and_in_range userBetInput, limit)) {
        print "Incorrect input. Please, enter your bet (1-$limit): "
        userBetInput = reader.readLine()
    }

    println "Player bets \$$userBetInput"
    userBetInput.toInteger()
}

def bet_is_valid_and_in_range(String bet, Integer limit) {
    bet.isInteger() && (bet.toInteger() > 0 && bet.toInteger() <= limit)
}

def offer_action(BufferedReader reader) {
    print 'Choose your action - (H)it, (S)tand or (D)ouble: '
    def userActionInput = reader.readLine()
    while (!(userActionInput ==~ /[HDS]/)) {
        print 'Incorrect input. Please, choose your action - (H)it, (S)tand or (D)ouble: '
        userActionInput = reader.readLine()
    }
    return switch (userActionInput) {
        case 'D' -> 'Double'
        case 'H' -> 'Hit'
        case 'S' -> 'Stand'
        default -> throw new IllegalStateException()
    }
}

def do_player_turn(String action, hand, deck) {
    println "Player ${action.toLowerCase()}s"
    switch (action) {
        case 'Hit' -> {
            hand << deck.pop()
            true
        }
        case 'Double' -> {
            hand << deck.pop()
            false
        }
        case 'Stand' -> false
        default -> throw new IllegalArgumentException()
    }
}

def do_dealer_turn(hand, deck) {
    def isReadyToTakeCard = calculate_hand_score(hand) < 17
    if (isReadyToTakeCard) {
        println 'Dealer hits'
        hand << deck.pop()
    } else {
        println 'Dealer stands'
    }
    isReadyToTakeCard
}

