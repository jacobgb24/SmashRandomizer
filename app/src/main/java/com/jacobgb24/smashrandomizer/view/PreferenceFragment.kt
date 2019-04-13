package com.jacobgb24.smashrandomizer.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceFragmentCompat
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import com.jacobgb24.smashrandomizer.R
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import com.jacobgb24.smashrandomizer.BuildConfig
import com.jacobgb24.smashrandomizer.controller.MainActivity
import android.content.Intent
import android.net.Uri
import android.webkit.WebView
import androidx.appcompat.app.AlertDialog


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

        val versionPref = findPreference("pref_version")
        versionPref.title = "Version ${BuildConfig.VERSION_NAME}"

        val githubPref = findPreference("pref_github")
        githubPref.setOnPreferenceClickListener {
            try {
                startActivity(
                    Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/jacobgb24/SmashRandomizer"))
                )
            } catch (e: Exception) {
                Toast.makeText(activity, "Could not open link", Toast.LENGTH_SHORT).show()
            }
            true
        }

        val glidePref = findPreference("pref_glide")
        glidePref.setOnPreferenceClickListener {
            webDialog("file:///android_asset/legal/glide_license.html")
            true
        }

        val legalPref = findPreference("pref_legal")
        legalPref.setOnPreferenceClickListener {
            webDialog("file:///android_asset/legal/legal_disclaimer.html")
            true
        }

        val privacyPolicyPref = findPreference("pref_privacy_policy")
        privacyPolicyPref.setOnPreferenceClickListener {
            webDialog("file:///android_asset/legal/privacy_policy.html")
            true
        }
    }

    private fun webDialog(url: String) {
        val view = WebView(activity)
        view.loadUrl(url)
        val alertDialog = AlertDialog.Builder(context!!)
            .setView(view)
            .setPositiveButton("ok", null)
            .create()
        alertDialog.show()
    }


}


