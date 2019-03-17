package com.jacobgb24.smashrandomizer

import android.content.Context
import android.util.Log

data class Character(val name: String, val imageName: String)



fun generateCharacters(context: Context): ArrayList<Character> {
    var characters = ArrayList<Character>()

    var portraits = context.getAssets().list("portraits")
    for (image in portraits) {
        var name = ""
        name += image[0].toUpperCase()
        for (char in image.substring(1, image.indexOf("."))) {
            if (char.isLowerCase()) {
                name += char
            }
            else {
                name += " " + char.toUpperCase()
            }

        }
        characters.add(Character(name, image))
    }

    return characters
}