package com.jacobgb24.smashrandomizer.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.preference.PreferenceFragmentCompat
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import com.jacobgb24.smashrandomizer.R
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import com.jacobgb24.smashrandomizer.controller.MainActivity


class PreferenceFragment: PreferenceFragmentCompat() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
//        view!!.setBackgroundColor(resources.getColor(android.R.color.background_light ))
        setHasOptionsMenu(true)
        (activity as MainActivity).supportActionBar!!.title = "Settings"

        return view
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, null)

        val repeatPref = findPreference("pref_allow_repeat")
        repeatPref.setOnPreferenceClickListener {
            Toast.makeText(activity, "This does nothing right now", Toast.LENGTH_SHORT).show()
            true
        }
    }




}


