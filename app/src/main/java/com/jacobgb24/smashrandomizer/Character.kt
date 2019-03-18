package com.jacobgb24.smashrandomizer

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.Log

class Character(val name: String, val imageName: String, var isSelected: Boolean = true) {

    fun getIconUri(): Uri {
        return Uri.parse("file:///android_asset/icons/$imageName")

    }

    fun getPortraiUri(): Uri {
        return Uri.parse("file:///android_asset/portraits/$imageName")
    }

    fun toggle() {
        isSelected = !isSelected
    }

}


fun generateCharacters(context: Context): ArrayList<Character> {
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

        characters.add(Character(name, image))
    }

    return characters
}

fun getNameFromPath(image: String): String {
    var name = "${image[0].toUpperCase()}"
    for (char in image.substring(1, image.indexOf("."))) {
        when {
            char.isLowerCase() -> name += char
            else -> name += " $char"
        }
    }
    return name
}