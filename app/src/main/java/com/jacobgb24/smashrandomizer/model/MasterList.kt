package com.jacobgb24.smashrandomizer.model

import android.content.Context

/** State and Logic for the Master Character List.
 *
 * Variables:
 * + mainCharacterList: The list of characters available in SSBU
 *
 * Methods:
 * + sortByAppearance() - sorts the characters by their in-game selection menu order.
 * + getByAppearance(appearance: Number) - get a character based on their in-game selection menu order.
 * + rawRepresentation() - produces a stripped down copy of the character list with only the appearanceNumber and
 *      inclusion in the pool
 * + generateCharacters() - initialize the master list. Must be done before calling other methods.
 */

// Must be initialized before using any functions. Do so via `mainCharacterList = generateCharacters(context)`
lateinit var mainCharacterList: ArrayList<Character>

// Sorts the list by the character numbers in game.
fun ArrayList<Character>.sortByAppearance() = sortBy { it.appearanceOrder.toDouble() }

// Get a character from the main list by it's appearance number.
fun ArrayList<Character>.getByAppearance(appearance: Number): Character {
    val char = filter {it.appearanceOrder == appearance}
    return char[0]
}

// Strip away character name and images.
fun ArrayList<Character>.rawRepresentation() = ArrayList(map {Pair(it.appearanceOrder, true)})

// call to initialize master list
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
            "rosalina&Luma.webp" -> "Rosalina & Luma"
            "pokemonTrainer.webp" -> "Pokémon Trainer"
            else -> getNameFromPath(image)
        }

        characters.add(Character(name, image, context))
    }
    characters.sortByAppearance()
    return characters
}
