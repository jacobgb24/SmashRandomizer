package com.jacobgb24.smashrandomizer.model

import android.util.Log
import kotlinx.android.synthetic.main.fragment_ironman.view.*
import kotlin.math.min

class Ironman {

    private var chars: ArrayList<Character> = activePool.getSelected()
    private var position = 0
    private val deckSize = 5 // should match numColumns of the gridview

    init {
        chars.shuffle()
    }

    fun currentChar(): Character {
        return chars[position]
    }

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
}