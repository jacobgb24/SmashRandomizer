package com.jacobgb24.smashrandomizer

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var adapter: CharacterSelectionAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var characters = generateCharacters(this)
        adapter = CharacterSelectionAdapter(this, characters)

        grid_character_selection.adapter = adapter
    }
}
