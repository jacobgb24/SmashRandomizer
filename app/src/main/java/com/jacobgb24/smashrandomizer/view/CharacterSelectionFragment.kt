package com.jacobgb24.smashrandomizer.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jacobgb24.smashrandomizer.controller.CharacterSelectionAdapter
import com.jacobgb24.smashrandomizer.R
import kotlinx.android.synthetic.main.fragment_character_selection.view.*


class CharacterSelectionFragment : Fragment() {

    private lateinit var adapter: CharacterSelectionAdapter

    // Factory method
    companion object {
        fun newInstance(): CharacterSelectionFragment {
            return CharacterSelectionFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    // Create view hierarchy controlled by fragment.
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_character_selection, container, false)
        adapter = CharacterSelectionAdapter(view.context)
        view.grid_character_selection.adapter = adapter

        return view

    }

}
