package com.jacobgb24.smashrandomizer.model

import android.content.Context
import com.jacobgb24.smashrandomizer.R
import java.util.*
import kotlin.collections.ArrayList

// Must be initialized before using any functions. Do so via `mainCharacterList = generateCharacters(context)`
lateinit var mainCharacterList: ArrayList<Character>


fun ArrayList<Character>.getSelected(): ArrayList<Character> = ArrayList(filter { character -> character.isSelected })

fun ArrayList<Character>.getNewRandom(current: Character?): Character {
    var nextChar: Character
    val selected = getSelected()
    do {
        nextChar = selected[Random().nextInt(selected.size)]
    } while(current?.equals(nextChar) == true) // have to do == true to make current being null = false
    return nextChar

}

fun ArrayList<Character>.setAllSelection(selected: Boolean) = forEach { it.isSelected = selected }

fun ArrayList<Character>.sortByAppearance() = sortBy { it.appearanceOrder.toDouble() }


fun generateCharacters(context: Context): ArrayList<Character> {

    fun getNameFromPath(image: String): String {
        var name = "${image[0].toUpperCase()}"
        for (char in image.substring(1, image.indexOf("."))) {
            name += when {
                char.isLowerCase() -> char
                else -> " $char"
            }
        }
        return name
    }

    val characters = ArrayList<Character>()

    val portraits = context.assets.list("portraits")
    for (image in portraits) {
        val name = when(image) {
            // handle special cases, otherwise do default parsing
            "kingKRool.webp" -> "King K. Rool"
            "rob.webp" -> "R.O.B."
            "drMario.webp" ->"Dr. Mario"
            "bowserJr.webp" -> "Bowser Jr."
            "mrGame&Watch.webp" -> "Mr. Game & Watch"
            "pacman.webp" -> "PAC-MAN"
            "pokemonTrainer.webp" -> "Pokémon Trainer"
            else -> getNameFromPath(image)
        }

        characters.add(Character(name, image, context))
    }
    characters.sortByAppearance()
    return characters
}

fun saveSelections(context: Context) {
    val sharedPreferences = context.getSharedPreferences(context.getString(R.string.selection_file_key), Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    for (character in mainCharacterList) {
        editor.putBoolean(character.imageName, character.isSelected)
    }
    editor.apply()
}

fun loadSelection(context: Context) {
    val sharedPreferences = context.getSharedPreferences(context.getString(R.string.selection_file_key), Context.MODE_PRIVATE)
    for (character in mainCharacterList) {
        character.isSelected = sharedPreferences.getBoolean(character.imageName, true)
    }
}

