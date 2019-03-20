package com.jacobgb24.smashrandomizer

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var currentCharacter: Character? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainCharacterList = generateCharacters(this)

        setRandomChar()

        button_random.setOnClickListener {
            setRandomChar()
        }
    }


    private fun setRandomChar() {
        currentCharacter = mainCharacterList.getNewRandom(currentCharacter)
        Glide.with(this).load(currentCharacter!!.portraitUri).into(image_character_random)
        text_character_name_random.text = currentCharacter!!.name
        image_character_random.contentDescription = "Current Character: ${currentCharacter!!.name}"
    }
}
