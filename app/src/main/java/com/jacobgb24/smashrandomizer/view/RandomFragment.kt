package com.jacobgb24.smashrandomizer.view

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.RippleDrawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.*
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.jacobgb24.smashrandomizer.*
import com.jacobgb24.smashrandomizer.controller.MainActivity
import com.jacobgb24.smashrandomizer.model.Character
import com.jacobgb24.smashrandomizer.model.getNewRandom
import com.jacobgb24.smashrandomizer.model.mainCharacterList
import kotlinx.android.synthetic.main.fragment_random.*
import kotlinx.android.synthetic.main.fragment_random.view.*


class RandomFragment : Fragment() {

    var currentCharacter: Character? = null




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    // Create view hierarchy controlled by fragment.
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_random, container, false)
        Log.e("RandomFrag", "onCreateView")
        view.button_random.addRippleFG()
        view.button_iron_man.addRippleFG()
        view.button_view_pool.addRippleFG()

        view.button_random.setOnClickListener {
            setRandomChar()
        }
        view.button_view_pool.setOnClickListener {
            (activity as MainActivity).addFragment(CurrentPoolFragment())
        }

        return view

    }

    override fun onStart() {
        setRandomChar()
        super.onStart()
    }



    private fun setRandomChar() {
        currentCharacter = mainCharacterList.getNewRandom(currentCharacter)
        Glide.with(this).load(currentCharacter!!.portraitUri).override(1200, 1200)
            .transition(DrawableTransitionOptions.withCrossFade()).into(image_character_random)
        text_character_name_random.text = currentCharacter!!.name
        text_character_number_random.text = currentCharacter!!.getOrderString()
        image_character_random.setHelp("Current Character: ${currentCharacter!!.name}")
    }


    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater!!.inflate(R.menu.main_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.menu_open_settings -> {
                (activity as MainActivity).addFragment(PreferenceFragment())
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


}
