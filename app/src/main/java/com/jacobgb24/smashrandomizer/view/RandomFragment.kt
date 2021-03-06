package com.jacobgb24.smashrandomizer.view

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.jacobgb24.smashrandomizer.R
import com.jacobgb24.smashrandomizer.addRippleFG
import com.jacobgb24.smashrandomizer.controller.MainActivity
import com.jacobgb24.smashrandomizer.model.Character
import com.jacobgb24.smashrandomizer.model.activePool
import com.jacobgb24.smashrandomizer.model.loadDefaultCharacter
import com.jacobgb24.smashrandomizer.setHelp
import com.jacobgb24.smashrandomizer.view.ironman.IronmanStartupFragment
import kotlinx.android.synthetic.main.fragment_random.view.*


class RandomFragment : Fragment() {

    private lateinit var currentCharacter: Character

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        currentCharacter = loadDefaultCharacter(this.context!!)
    }

    // Create view hierarchy controlled by fragment.
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_random, container, false)
        (activity as MainActivity).supportActionBar!!.title = getString(R.string.smash_randomizer)

        view.button_random.addRippleFG()
        view.button_iron_man.addRippleFG()
        view.button_view_pool.addRippleFG()

        view.button_random.setOnClickListener {
            setRandomChar()
        }
        view.button_iron_man.setOnClickListener {
            (activity as MainActivity).addFragment(IronmanStartupFragment())
        }
        view.button_view_pool.setOnClickListener {
            (activity as MainActivity).addFragment(PoolListFragment())
        }

        view.text_pool_name.text = activePool.name

        setCurrentChar(view)

        return view

    }

    private fun setRandomChar(view: View = this.view!!) {
        currentCharacter = activePool.getNewRandom(currentCharacter)
        setCurrentChar(view)
    }


    private fun setCurrentChar(view: View = this.view!!) {
        Glide.with(this).load(currentCharacter.portraitUri)
            .override(1200, 1200)
            .transition(DrawableTransitionOptions.withCrossFade())
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .into(view.image_character_random)

        view.text_character_name_random.text = currentCharacter.name
        view.text_character_number_random.text = currentCharacter.getOrderString()
        view.image_character_random.setHelp("Current Character: ${currentCharacter.name}")
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
