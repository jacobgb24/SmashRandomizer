package com.jacobgb24.smashrandomizer.controller

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.jacobgb24.smashrandomizer.*
import com.jacobgb24.smashrandomizer.model.generateCharacters
import com.jacobgb24.smashrandomizer.model.loadSelection
import com.jacobgb24.smashrandomizer.model.mainCharacterList
import com.jacobgb24.smashrandomizer.model.saveSelections
import com.jacobgb24.smashrandomizer.view.RandomFragment


/*
TODO: make view pool button attach the current pool fragment instead of the character selection fragment
TODO: add icons for the view pool button and iron man button
TODO: make fancy pants randomize button
TODO: make fancy pants app icon
 */
class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_random)
        mainCharacterList = generateCharacters(this)
        loadSelection(this)

        addFragment(RandomFragment())
    }


    override fun onPause() {
        super.onPause()
        saveSelections(this)
    }

}



