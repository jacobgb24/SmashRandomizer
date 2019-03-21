package com.jacobgb24.smashrandomizer.views

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.jacobgb24.smashrandomizer.*
import kotlinx.android.synthetic.main.activity_main.*


/*
TODO: make view pool button attach the current pool fragment instead of the character selection fragment
TODO: add icons for the view pool button and iron man button
TODO: make fancy pants randomize button
TODO: make fancy pants app icon
 */
class MainActivity : AppCompatActivity() {

    var currentCharacter: Character? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainCharacterList = generateCharacters(this)
        loadSelection(this)

        setRandomChar()

        button_random.setOnClickListener {
            setRandomChar()
        }

        button_view_pool.setOnClickListener {
            addFragment(CharacterSelectionFragment.newInstance(), android.R.id.content)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.menu_open_settings -> {
                startActivity(Intent(this, PreferenceActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    private fun setRandomChar() {
        currentCharacter = mainCharacterList.getNewRandom(currentCharacter)
        Glide.with(this).load(currentCharacter!!.portraitUri)
            .transition(withCrossFade()).into(image_character_random)
        text_character_name_random.text = currentCharacter!!.getOrderString().append(" - ${currentCharacter!!.name}")
        image_character_random.contentDescription = "Current Character: ${currentCharacter!!.name}"
    }

    override fun onPause() {
        super.onPause()
        saveSelections(this)
    }

}



