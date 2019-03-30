package com.jacobgb24.smashrandomizer.model

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.RelativeSizeSpan
import android.text.style.SuperscriptSpan
import android.util.Log
import java.io.Serializable
import kotlin.math.floor


lateinit var currentCharacter: Character

class Character(val name: String, val imageName: String, val context: Context) {

    // use these to load images via Glide.with(context).load(character.portraitUri).into(view)
    val iconUri: Uri = Uri.parse("file:///android_asset/icons/$imageName")
    var portraitUri: Uri = Uri.parse("file:///android_asset/portraits/$imageName")
    var appearanceOrder = getAppearanceOrder()

    fun getOrderString(): SpannableStringBuilder  {

        if (appearanceOrder == -1) {
            // Default On-Start Character
            val build = SpannableStringBuilder()
            build.append("   ?")
            return build
        }

        val build = SpannableStringBuilder("${floor(appearanceOrder.toDouble()).toInt()}")
        if (appearanceOrder.toDouble() % 1 != 0.0) {
            build.append("ε")
            build.setSpan(SuperscriptSpan(), build.length - 1, build.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            build.setSpan(RelativeSizeSpan(.6f), build.length - 1, build.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        //increase start padding for uniformity
        for(i in 1..(3 - build.length)) {
            build.insert(0, " ")
        }
        return build
    }

    //this will get stored in memory, but I don't notice a difference in ram usage and it is faster
    val iconDrawable: Drawable by lazy {
        Drawable.createFromStream(context.assets.open("icons/$imageName"), null)

    }


    fun copy(): Character {
        return Character(name, imageName, context)
    }

}

/**
 * Create a default "?" character object that loads on application start.
 */
fun loadDefaultCharacter(context: Context): Character {
    // get the default startup image
    val defaultImage = Uri.parse("file:///android_asset/startup/temp.jpg")
    val char = Character("", "", context)
    char.appearanceOrder = -1
    char.portraitUri = defaultImage
    return char
}


/**
 * Gets a characters number according to their appearance in smash (the way they're sorted in game).
 * Echo character are x.5.
 * Returns a number which can be cast to int/
 */
fun Character.getAppearanceOrder(): Number {
    return when(imageName) {
        "bayonetta.webp" -> 63
        "bowser.webp" -> 14
        "bowserJr.webp" -> 58
        "captainFalcon.webp" -> 11
        "chrom.webp" -> 25.5
        "cloud.webp" -> 61
        "corrin.webp" -> 62
        "daisy.webp" -> 13.5
        "darkPit.webp" -> 28.5
        "darkSamus.webp" -> 4.5
        "diddyKong.webp" -> 36
        "donkeyKong.webp" -> 2
        "drMario.webp" -> 18
        "duckHunt.webp" -> 59
        "falco.webp" -> 20
        "fox.webp" -> 7
        "ganondorf.webp" -> 23
        "greninja.webp" -> 50
        "iceClimbers.webp" -> 15
        "ike.webp" -> 32
        "incineroar.webp" -> 69
        "inkling.webp" -> 64
        "isabelle.webp" -> 68
        "jigglypuff.webp" -> 12
        "ken.webp" -> 60.5
        "kingDedede.webp" -> 39
        "kingKRool.webp" -> 67
        "kirby.webp" -> 6
        "link.webp" -> 3
        "littleMac.webp" -> 49
        "lucario.webp" -> 41
        "lucas.webp" -> 37
        "lucina.webp" -> 21.5
        "luigi.webp" -> 9
        "mario.webp" -> 1
        "marth.webp" -> 21
        "megaMan.webp" -> 46
        "metaKnight.webp" -> 27
        "mewtwo.webp" -> 24
        "miiBrawler.webp" -> 51
        "miiGunner.webp" -> 53
        "miiSwordfighter.webp" -> 52
        "mrGame&Watch.webp" -> 26
        "ness.webp" -> 10
        "olimar.webp" -> 40
        "pacman.webp" -> 55
        "palutena.webp" -> 54
        "peach.webp" -> 13
        "pichu.webp" -> 19
        "pikachu.webp" -> 8
        "piranhaPlant.webp" -> 70
        "pit.webp" -> 28
        "pokemonTrainer.webp" -> 33
        "richter.webp" -> 66.5
        "ridley.webp" -> 65
        "rob.webp" -> 42
        "robin.webp" -> 56
        "rosalina&Luma.webp" -> 48
        "roy.webp" -> 25
        "ryu.webp" -> 60
        "samus.webp" -> 4
        "sheik.webp" -> 16
        "shulk.webp" -> 57
        "simon.webp" -> 66
        "snake.webp" -> 31
        "sonic.webp" -> 38
        "toonLink.webp" -> 43
        "villager.webp" -> 45
        "wario.webp" -> 30
        "wiiFitTrainer.webp" -> 47
        "wolf.webp" -> 44
        "yoshi.webp" -> 5
        "youngLink.webp" -> 22
        "zelda.webp" -> 17
        "zeroSuitSamus.webp" -> 29
        else -> Int.MAX_VALUE
    }
}