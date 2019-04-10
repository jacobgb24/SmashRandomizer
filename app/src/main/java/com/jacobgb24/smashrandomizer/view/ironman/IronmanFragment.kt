package com.jacobgb24.smashrandomizer.view.ironman

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.jacobgb24.smashrandomizer.R
import com.jacobgb24.smashrandomizer.addRippleFG
import com.jacobgb24.smashrandomizer.controller.CharactersAdapter
import com.jacobgb24.smashrandomizer.controller.MainActivity
import com.jacobgb24.smashrandomizer.getBoolPref
import com.jacobgb24.smashrandomizer.model.Ironman
import com.jacobgb24.smashrandomizer.setHelp
import kotlinx.android.synthetic.main.fragment_ironman.view.*


class IronmanFragment : Fragment() {

    val TAG = "IRONMAN_START"
    private var ironman = Ironman()
    private lateinit var deckAdapter: CharactersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    // Create view hierarchy controlled by fragment.
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_ironman, container, false)
        (activity as MainActivity).supportActionBar!!.title = "Ironman"
        deckAdapter = CharactersAdapter(activity!!, ironman.getDeck())
        view.grid_ironman_deck.adapter = deckAdapter
        view.button_ironman_lose.addRippleFG()
        view.button_ironman_win.addRippleFG()
        view.button_ironman_win.setOnClickListener {
            onWinClick()
        }
        view.button_ironman_lose.setOnClickListener {
            startResults(false)
        }
        setCurrentChar(view)

        if (getBoolPref("pref_hide_deck", view.context)) {
            view.layout_ironman_deck.visibility = View.GONE
        }
        else {
            view.layout_ironman_deck.visibility = View.VISIBLE
        }

        return view
    }

    private fun setCurrentChar(view: View = this.view!!) {
        Glide.with(this).load(ironman.currentChar().portraitUri).override(1200, 1200)
            .transition(DrawableTransitionOptions.withCrossFade()).into(view.image_character_ironman)
        view.text_character_name_ironman.text = ironman.currentChar().name
        view.text_character_number_ironman.text = ironman.currentChar().getOrderString()
        view.image_character_ironman.setHelp("Current Character: ${ironman.currentChar().name}")
    }

    private fun onWinClick() {
        if (ironman.playerWon()) {
            startResults(true)
        }
        else {
            ironman.progress()
            setCurrentChar()
            deckAdapter.characterList = ironman.getDeck()
            deckAdapter.notifyDataSetChanged()
        }
    }

    private fun startResults(won: Boolean) {
        if (won) ironman.position += 1
        val resultsFrag = IronmanResultsFragment.newInstance(ironman)
        (activity as MainActivity).addFragment(resultsFrag, trueReplace = true)
    }
}
