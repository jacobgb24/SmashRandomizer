package com.jacobgb24.smashrandomizer.view.ironman

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import android.widget.Toast
import com.jacobgb24.smashrandomizer.R
import com.jacobgb24.smashrandomizer.controller.CharactersAdapter
import com.jacobgb24.smashrandomizer.controller.MainActivity
import com.jacobgb24.smashrandomizer.model.activePool
import com.jacobgb24.smashrandomizer.view.PoolListFragment
import kotlinx.android.synthetic.main.fragment_ironman_startup.view.*


class IronmanStartupFragment : Fragment() {

    val TAG = "IRONMAN_START"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    // Create view hierarchy controlled by fragment.
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_ironman_startup, container, false)
        (activity as MainActivity).supportActionBar!!.title = "Ironman"
        view.text_ironman_start_pool.text = "Pool: ${activePool.name}"
        view.grid_ironman_start_chars.adapter = CharactersAdapter(activity!!, activePool.getSelected())
        view.button_ironman_start_change_pool.setOnClickListener {
            (activity as MainActivity).addFragment(PoolListFragment())
        }
        view.button_ironman_start_begin.setOnClickListener {
            (activity as MainActivity).addFragment(IronmanFragment())
        }

        return view

    }
}
