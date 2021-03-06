package com.jacobgb24.smashrandomizer.controller

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
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
    private val fragTag = "MAIN"

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

    fun addFragment(fragment: Fragment, tag: String? = null, trueReplace: Boolean = false) {
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        if(trueReplace) {
            supportFragmentManager.popBackStackImmediate()
        }
        supportFragmentManager.inTransaction {

            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                .replace(android.R.id.content, fragment, tag).addToBackStack(tag)

        }
    }

    fun removeFragment() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStackImmediate()
            Log.e(fragTag, "removed frag. count ${supportFragmentManager.backStackEntryCount}")
            if (supportFragmentManager.backStackEntryCount == 0) {
                supportActionBar!!.setDisplayHomeAsUpEnabled(false)
            }
        }
    }

    fun removeAllFragments() {
        while (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStackImmediate()
        }
        supportActionBar!!.setDisplayHomeAsUpEnabled(false)
    }

    override fun onBackPressed() {
        Log.e(fragTag, "onBackCalled")

        val fragment = supportFragmentManager.findFragmentById(android.R.id.content)

        // if the fragment doesn't implement FragOnBackPressed or it returned false from that fun
        if ((fragment as? FragOnBackPressed)?.onBackPressed() != true) {
            if (supportFragmentManager.backStackEntryCount > 0) {
                removeFragment()
            }
            else {
                super.onBackPressed()
            }
        }

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        Log.e(fragTag, "Menu: ${item.toString()} clicked")
        when(item!!.itemId) {
            android.R.id.home -> onBackPressed()
            else -> return super.onOptionsItemSelected(item)
        }
        return super.onOptionsItemSelected(item)
    }

}


// Higher order transaction function. Used by AppCompatActivity extensions below.
inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
    beginTransaction().func().commit()
}


/**
 * A Fragment may implement this interface to have control over the back button.
 * Implementation is optional otherwise
 */
interface FragOnBackPressed {
    fun onBackPressed(): Boolean
}



