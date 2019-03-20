package com.jacobgb24.smashrandomizer

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/*
TODO: define layout for this fragment
TODO: write list adapter for current pool
TODO: figure out how to transition from this fragment to the character select fragment.
*/
class CurrentPoolFragment : Fragment() {

    // Factory method
    companion object {
        fun newInstance(): CharacterSelectionFragment {
            return CharacterSelectionFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_current_pool, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }



}
