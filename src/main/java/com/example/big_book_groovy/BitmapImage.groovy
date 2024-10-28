#!/usr/local/groovy
package com.example.big_book_groovy

import java.util.stream.Collectors

def bitmap = '''H....................................................................
   **************   *  *** **  *      ******************************
  ********************* ** ** *  * ****************************** *
 **      *****************       ******************************
          *************          **  * **** ** ************** *
           *********            *******   **************** * *
            ********           ***************************  *
   *        * **** ***         *************** ******  ** *
               ****  *         ***************   *** ***  *
                 ******         *************    **   **  *
                 ********        *************    *  ** ***
                   ********         ********          * *** ****
                   *********         ******  *        **** ** * **
                   *********         ****** * *           *** *   *
                     ******          ***** **             *****   *
                     *****            **** *            ********
                    *****             ****              *********
                    ****              **                 *******   *
                    ***                                       *    *
                    **     *                    *
....................................................................'''

println '''
Bitmap Message, displays a text message according to the provided bitmap image.
Enter the message to display with the bitmap.
'''

def swapCharOrDefault = (char expected, char replacement) -> expected == '*' as char ? replacement : ' '

def replaceLine = (String line, String replacement) -> {
    def buffer = new StringBuilder(line.size())
    if (line.startsWith '.') {
        buffer.append line
    } else {
        (0..line.size()-1).forEach { index ->
            def bitMapChar = line.charAt index
            def replacementChar = replacement.charAt index % replacement.size()
            def result = swapCharOrDefault bitMapChar, replacementChar
            buffer.append result
        }
    }
    buffer.toString()
}

try (def reader = new BufferedReader(new InputStreamReader(System.in))) {
    print 'Message: '
    def userMessageInput = reader.readLine()
    println bitmap.lines().map({ replaceLine it, userMessageInput }).collect(Collectors.joining('\n'))
}
