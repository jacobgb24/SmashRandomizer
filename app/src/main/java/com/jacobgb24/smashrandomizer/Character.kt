package com.jacobgb24.smashrandomizer

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.Log

class Character(val name: String, val imageName: String, val context: Context, var isSelected: Boolean = true) {
    // use these to load images via Glide.with(context).load(character.portraitUri).into(view)
    val iconUri: Uri = Uri.parse("file:///android_asset/icons/$imageName")
    val portraitUri: Uri = Uri.parse("file:///android_asset/portraits/$imageName")

    //this will get stored in memory, but I don't notice a different in ram usage and it is faster
    val iconDrawable: Drawable by lazy {
        Drawable.createFromStream(context.assets.open("icons/$imageName"), null)

    }

    fun toggle() {
        isSelected = !isSelected
    }

}

