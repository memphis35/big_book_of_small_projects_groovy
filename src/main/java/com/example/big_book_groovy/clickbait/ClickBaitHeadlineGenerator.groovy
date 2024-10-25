#!/usr/local/groovy

package com.example.big_book_groovy.clickbait

def random = new Random()

def pickRandomItem = { List<String> collection ->
    collection.get(random.nextInt(collection.size()))
}
def nouns = ['Robots', 'Reptiles', 'Aliens', 'Martians', 'Lunatics',
             'Ghosts', 'Zombies', 'Dragons', 'Unicorns', 'Borgs'].asUnmodifiable()
def objects = ['Perpetual Engine', 'Humanity', 'Nuclear Weapon', 'Concealed Government', 'Sexual Relationship',
               'Carrot', 'Movie Industry', 'HIV', 'Flat Earth', 'COVID-19'].asUnmodifiable()
def places = ['Restaurant', 'The Rear Side Of The Universe', 'ParkingLot', 'Your House', 'Commercial Center',
              'Gas Station', 'The Upside Down', 'The White House', 'Hospital', 'Airport'].asUnmodifiable()
def whens = ['Today', 'Tomorrow', 'Next Weekend', 'Soon', 'RIGHT NOW', 'Next Year', 'Next Week', 'Before The Earth Collapsed'].asUnmodifiable()

def millennials = { ->
    def noun = pickRandomItem nouns
    "Are Millennials Killing the $noun Industry?"
}

def withoutThis = { ->
    def noun = pickRandomItem nouns
    def object = pickRandomItem objects
    def when = pickRandomItem whens
    "Without This $object, $noun Could Kill You $when"
}

def whatDontYouKnow = { ->
    def noun = pickRandomItem nouns
    def object = pickRandomItem objects
    "What $noun Don't Want You To Know About $object"
}

def giftIdeas = { ->
    def noun = pickRandomItem nouns
    def place = pickRandomItem places
    "${random.nextInt(10)} Gift Ideas to Give Your $noun From $place"
}

def surprise = { ->
    def reasonsNumber = random.nextInt(50, 101)
    def reasonNumber = random.nextInt(0, reasonsNumber + 1)
    def noun = pickRandomItem nouns
    "$reasonsNumber Reasons Why $noun Are More Interesting Than You Think (Number $reasonNumber Will Surprise You!)"
}
// SCRIPT START POINT
println 'Clickbait Headline Generator, by Aleksandr Smirnov [aa.smirnov2@gmail.com]'

try (def reader = new BufferedReader(new InputStreamReader(System.in))) {
    println 'Our website needs to trick people into looking at ads!'
    println 'Enter the number of clickbait headlines to generate: (1-10)'
    print '>>> '
    def userInputQuantity = reader.readLine()
    while (!(userInputQuantity.isInteger() || !(1..10).contains(userInputQuantity.toInteger()))) {
        println 'error'
        userInputQuantity = reader.readLine()
    }
    def quantity = userInputQuantity.toInteger()

    (1..quantity).forEach {
        def randomHeadlineNumber = random.nextInt(5)
        def headline = switch (randomHeadlineNumber) {
            case 0 -> millennials()
            case 1 -> withoutThis()
            case 2 -> whatDontYouKnow()
            case 3 -> giftIdeas()
            case 4 -> surprise()
            default -> {}
        }
        println headline
    }
    def websites = ['Wobblesite', 'Blogg', 'Bookface', 'Goggles', 'Twister', 'Pastagram']
    def website = pickRandomItem websites
    def when = pickRandomItem whens
    println "Post these to our $website ${when.toLowerCase()}, or you're fired!"
}
