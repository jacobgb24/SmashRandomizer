package com.jacobgb24.smashrandomizer.model

import java.text.DecimalFormat
import kotlin.math.min

class Ironman {

    var chars: ArrayList<Character> = activePool.getSelected()
    var position = 0
    private val deckSize = 5 // should match numColumns of the gridview

    /**
     * Shuffle the character order at start
     */
    init {
        chars.shuffle()
    }

    fun currentChar(): Character {
        return chars[position]
    }

    /**
     * Gets the characters that should show in the deck at a given time
     */
    fun getDeck(): ArrayList<Character> {
        return ArrayList(chars.slice((position + 1)..min(chars.size - 1, position + deckSize)))
    }

    fun progress() {
        if( position < chars.size - 1) {
            position += 1
        }
    }

    fun playerWon(): Boolean {
        return position == chars.size - 1
    }

    /**
     * Returns the completion percentage as a string XX.X
     */
    fun getPercentage(): String {
        val df = DecimalFormat("###.##%")
        return if (chars.size > 0) df.format((position / chars.size.toDouble())) else "N/A"
    }
}