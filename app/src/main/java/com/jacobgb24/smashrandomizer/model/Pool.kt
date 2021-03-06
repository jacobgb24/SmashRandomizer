package com.jacobgb24.smashrandomizer.model

import android.util.Log
import java.io.Serializable
import java.util.*
import kotlin.collections.ArrayList

/** Pool Class. Used to manage custom defined pools.
 *
 * Public Accessors:
 * + getNewRandom(): Character
 * + get(pos: Int): Character
 * + contains(character: Character): Boolean
 * + size(): Int
 * + getSelected(): ArrayList<Character>
 *
 * Public Mutators:
 * + toggle(pos: Int)
 * + setAllSelection(selected: Boolean)
 * + sortByAppearance()
 */

data class Pool(var name: String): Serializable {
    // Stripped down to just the appearance number and whether or not their in the pool.
    private var characters: ArrayList<Pair<Number, Boolean>> = mainCharacterList.rawRepresentation()

    //----------------------------------------------------------------------------------------------------------------//
    // ACCESSORS                                                                                                      //
    //----------------------------------------------------------------------------------------------------------------//

    // Get a new random character!
    fun getNewRandom(current: Character?): Character {
        var charNum: Number
        val selected = getRawSelected()

        if (selected.size == 1){
            charNum = selected[0].first
        }
        else {
            do {
                charNum = selected[Random().nextInt(selected.size)].first
            } while(current?.appearanceOrder?.equals(charNum) == true) // have to do == true to make current being null = false
        }

        return mainCharacterList.getByAppearance(charNum)
    }

    // Gets the Character from the mainCharacterList associated with this index
    operator fun get(index: Int): Character {
        return mainCharacterList[index]
    }

    // checks if a character is "in" the pool
    fun contains(char: Character): Boolean {
        return characters.contains(Pair(char.appearanceOrder, true))
    }

    // Get the size of the pool.
    fun size(): Int {
        return getSelected().size
    }

    // Returns the list of Characters in the pool
    fun getSelected(): ArrayList<Character> {
        return ArrayList(mainCharacterList.filter { contains(it) })
    }

    //----------------------------------------------------------------------------------------------------------------//
    // MUTATORS                                                                                                       //
    //----------------------------------------------------------------------------------------------------------------//

    // Toggle whether or not the character at given position is in the pool.
    fun toggle(pos: Int) {
        characters[pos] = Pair(characters[pos].first, !characters[pos].second)
    }

    // Set all characters as either in or out of the pool.
    fun setAllSelection(selected: Boolean) {
        characters = ArrayList(characters.map { Pair(it.first, selected) })
    }

    // sorts the raw list by appearance order.
    fun sortByAppearance() {
        characters.sortBy { it.first.toDouble() }
    }

    //----------------------------------------------------------------------------------------------------------------//
    // PRIVATE                                                                                                        //
    //----------------------------------------------------------------------------------------------------------------//

    // Returns a list of character numbers and whether that character is in the pool.
    private fun getRawSelected(): ArrayList<Pair<Number, Boolean>> {
        return ArrayList(characters.filter { it.second })
    }

    fun checkNewCharacters() {
        val allCharacters = mainCharacterList.rawRepresentation()
        for (character in allCharacters) {
            if (character.first !in characters.map { it.first }) {
                characters.add(Pair(character.first, false))
            }
        }
    }



    fun copy(): Pool {
        Log.e("POOL", "Creating copy of $name")
        val newPool = Pool(name)
        newPool.characters = mainCharacterList.rawRepresentation()
        for ((pos, pair) in characters.withIndex()) {
            if (!pair.second) {
                newPool.toggle(pos)
            }
        }
        return newPool
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Pool

        if (name != other.name) return false
        if (characters != other.characters) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + characters.hashCode()
        return result
    }


}