package com.jacobgb24.smashrandomizer.view.ironman

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.jacobgb24.smashrandomizer.R
import com.jacobgb24.smashrandomizer.controller.CharactersAdapter
import com.jacobgb24.smashrandomizer.controller.IronmanResultsAdapter
import com.jacobgb24.smashrandomizer.controller.MainActivity
import com.jacobgb24.smashrandomizer.model.Ironman
import com.jacobgb24.smashrandomizer.setHelp
import kotlinx.android.synthetic.main.fragment_ironman.view.*
import kotlinx.android.synthetic.main.fragment_ironman_results.view.*


class IronmanResultsFragment : Fragment() {

    val TAG = "IRONMAN_START"
    val argString = "ironman"
    private lateinit var ironman: Ironman

    companion object {
        fun newInstance(ironman: Ironman): IronmanResultsFragment {
            val frag = IronmanResultsFragment()
            val args = Bundle()
            args.putSerializable(frag.argString, ironman)
            frag.arguments = args
            return frag
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ironman = arguments?.getSerializable(argString) as Ironman
        setHasOptionsMenu(true)
    }

    // Create view hierarchy controlled by fragment.
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_ironman_results, container, false)
        (activity as MainActivity).supportActionBar!!.title = "Ironman Results"
        view.grid_ironman_end_results.adapter = IronmanResultsAdapter(activity!!, ironman.chars, ironman.position)
        view.text_ironman_results_percent.text = "${ironman.getPercentage()}% Completion"
        view.button_ironman_results_home.setOnClickListener {
            (activity as MainActivity).removeAllFragments()
        }
        return view
    }

}
