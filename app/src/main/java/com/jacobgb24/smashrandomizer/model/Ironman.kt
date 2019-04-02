package com.jacobgb24.smashrandomizer.model

import android.util.Log
import kotlinx.android.synthetic.main.fragment_ironman.view.*
import java.io.Serializable
import kotlin.math.min

class Ironman: Serializable {

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

    fun isOver(): Boolean {
        return position == chars.size - 1
    }

    /**
     * Returns the completion percentage as a string XX.X
     */
    fun getPercentage(): String {
        Log.e("PERCENT", "$position / ${chars.size} = ${position / chars.size.toDouble()}")
        return if (chars.size > 0) "%.1f".format((position / chars.size.toDouble()) * 100) else "N/A"
    }
}