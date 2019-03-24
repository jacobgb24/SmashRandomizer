package com.jacobgb24.smashrandomizer.controller

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.util.Log
import android.view.MenuItem
import com.jacobgb24.smashrandomizer.model.generateCharacters
import com.jacobgb24.smashrandomizer.model.loadPools
import com.jacobgb24.smashrandomizer.model.mainCharacterList
import com.jacobgb24.smashrandomizer.model.savePools
import com.jacobgb24.smashrandomizer.view.RandomFragment


/*
TODO: make view pool button attach the current pool fragment instead of the character selection fragment
TODO: add icons for the view pool button and iron man button
TODO: make fancy pants randomize button
TODO: make fancy pants app icon
 */
class MainActivity : AppCompatActivity() {
    val TAG = "MAIN"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainCharacterList = generateCharacters(this)
        loadPools(this)


        setBaseFragment()


    }


    override fun onPause() {
        super.onPause()
        savePools(this)
    }

    private fun setBaseFragment() {
        if (supportFragmentManager.fragments.size == 0 ) {
            supportFragmentManager.inTransaction {
                add(android.R.id.content, RandomFragment())
            }
        }
    }

    fun addFragment(fragment: Fragment, tag: String? = null) {
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportFragmentManager.inTransaction {
            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                .replace(android.R.id.content, fragment, tag).addToBackStack(tag)
        }
    }

    fun removeFragment() {
        supportFragmentManager.popBackStackImmediate()
        Log.e(TAG, "removed frag. count ${supportFragmentManager.backStackEntryCount}")
        if (supportFragmentManager.backStackEntryCount == 0) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        }
    }

    override fun onBackPressed() {
        Log.e(TAG, "onBackCalled")
        if (supportFragmentManager.backStackEntryCount > 0) {
            removeFragment()
        }
        else {
            super.onBackPressed()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        Log.e("MENU", item.toString())
        when(item!!.itemId) {
            android.R.id.home -> onBackPressed()
            else -> return super.onOptionsItemSelected(item)
        }
        return true

    }

}


// Higher order transaction function. Used by AppCompatActivity extensions below.
inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
    beginTransaction().func().commit()
}




