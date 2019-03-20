package com.jacobgb24.smashrandomizer

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_character_selection.*


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
        return inflater.inflate(R.layout.fragment_character_selection, container, false)
    }

    // Populate grid view when fragment attaches.
    override fun onAttach(context: Context?) {
        super.onAttach(context)
        adapter = CharacterSelectionAdapter(context!!)
        grid_character_selection.adapter = adapter
    }
}
