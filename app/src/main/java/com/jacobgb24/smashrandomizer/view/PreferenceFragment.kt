package com.jacobgb24.smashrandomizer.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.preference.PreferenceFragmentCompat
import com.jacobgb24.smashrandomizer.BuildConfig
import com.jacobgb24.smashrandomizer.R
import com.jacobgb24.smashrandomizer.controller.MainActivity
import com.jacobgb24.smashrandomizer.model.clearPools
import com.jacobgb24.smashrandomizer.playSound


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

        val clearPref = findPreference("pref_clear")
        clearPref.setOnPreferenceClickListener {
            val view = requireActivity().layoutInflater.inflate(R.layout.dialog_basic, null)
            view.setTitle(getString(R.string.clear_all_pool_data))
            view.setMessage(getString(R.string.clear_pool_message))
            val (negButton, posButton) = view.addButtons(getString(R.string.cancel), getString(R.string.clear))

            val dialog = createDialog(context!!, view)
            negButton.setOnClickListener {
                dialog.dismiss()
            }
            posButton.setOnClickListener {
                clearPools(context!!)
                dialog.dismiss()
            }
            dialog.show()

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

        val versionPref = findPreference("pref_version")
        versionPref.title = "Version ${BuildConfig.VERSION_NAME}"
        var versionClicks = 0
        versionPref.setOnPreferenceClickListener {
            versionClicks++
            if (versionClicks == 12) {
                playSound(activity!!, R.raw.wow, 500)
                versionClicks = 0
            }
            true
        }

        val githubPref = findPreference("pref_github")
        githubPref.setOnPreferenceClickListener {
            try {
                startActivity(
                    Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/jacobgb24/SmashRandomizer"))
                )
            } catch (e: Exception) {
                Toast.makeText(activity, getString(R.string.could_not_open_link), Toast.LENGTH_SHORT).show()
            }
            true
        }
    }

    private fun webDialog(url: String) {
        val view = WebView(activity)
        view.loadUrl(url)
        val alertDialog = AlertDialog.Builder(context!!)
            .setView(view)
            .setPositiveButton(getString(R.string.got_it), null)
            .create()
        alertDialog.show()
    }


}


