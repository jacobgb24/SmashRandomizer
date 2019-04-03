package com.jacobgb24.smashrandomizer.view.ironman

import android.media.MediaPlayer
import android.os.Bundle
import androidx.fragment.app.Fragment
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
import com.jacobgb24.smashrandomizer.playSound
import com.jacobgb24.smashrandomizer.setHelp
import kotlinx.android.synthetic.main.fragment_ironman.view.*
import kotlinx.android.synthetic.main.fragment_ironman_results.view.*


class IronmanResultsFragment : Fragment() {

    val TAG = "IRONMAN_START"
    private lateinit var ironman: Ironman

    companion object {
        fun newInstance(ironman: Ironman): IronmanResultsFragment {
            val frag = IronmanResultsFragment()
            frag.ironman = ironman
            return frag
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // hopefully saving the app if the ironman isn't set
        if (!this::ironman.isInitialized) {
            ironman = Ironman()
            (activity as MainActivity).removeAllFragments()

        }
        setHasOptionsMenu(true)
    }

    // Create view hierarchy controlled by fragment.
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_ironman_results, container, false)
        (activity as MainActivity).supportActionBar!!.title = "Ironman Results"
        view.grid_ironman_end_results.adapter = IronmanResultsAdapter(activity!!, ironman.chars, ironman.position)
        view.text_ironman_results_percent.text = "${ironman.getPercentage()} Completion"
        view.button_ironman_results_home.setOnClickListener {
            (activity as MainActivity).removeAllFragments()
        }

        if (ironman.getPercentage() == "100%") {
//            playSound(activity!!, R.raw.congrats)
            playSound(activity!!, R.raw.congrats, 1000)
        }
        return view
    }

}
