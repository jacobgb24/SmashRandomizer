package com.jacobgb24.smashrandomizer.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.*
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.jacobgb24.smashrandomizer.*
import com.jacobgb24.smashrandomizer.controller.MainActivity
import com.jacobgb24.smashrandomizer.model.Character
import com.jacobgb24.smashrandomizer.model.activePool
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
        view.button_iron_man.setOnClickListener {
            Toast.makeText(activity, "Ironman not implemented yet", Toast.LENGTH_SHORT).show()
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
        currentCharacter = activePool.getNewRandom(currentCharacter)
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
