package com.jacobgb24.smashrandomizer.view

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jacobgb24.smashrandomizer.R
import com.jacobgb24.smashrandomizer.controller.CharacterSelectionAdapter
import com.jacobgb24.smashrandomizer.controller.MainActivity
import com.jacobgb24.smashrandomizer.controller.PoolViewAdapter
import kotlinx.android.synthetic.main.fragment_character_selection.view.*
import kotlinx.android.synthetic.main.fragment_current_pool.view.*

/*
TODO: define layout for this fragment
TODO: write list adapter for current pool
TODO: figure out how to transition from this fragment to the character select fragment.
*/
class CurrentPoolFragment : Fragment() {


    private lateinit var adapter: PoolViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    // Create view hierarchy controlled by fragment.
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_current_pool, container, false)
        adapter = PoolViewAdapter(view.context)
        view.grid_pool.adapter = adapter

        view.button_change_pool.setOnClickListener {
            (activity as MainActivity).addFragment(CharacterSelectionFragment())
        }

        return view

    }



}
