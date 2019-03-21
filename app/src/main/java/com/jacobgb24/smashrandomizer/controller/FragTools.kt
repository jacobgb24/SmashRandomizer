package com.jacobgb24.smashrandomizer.controller

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.jacobgb24.smashrandomizer.view.RandomFragment

// Higher order transaction function. Used by AppCompatActivity extensions below.
inline fun FragmentManager.inTransaction(
    func: FragmentTransaction.() -> FragmentTransaction) {
    beginTransaction().func().commit()
}

// Extend AppCompatActivity to add/remove/replace fragments. Used by Main Activity
fun AppCompatActivity.addFragment(fragment: Fragment) {
    supportFragmentManager.inTransaction {
        add(android.R.id.content, fragment).addToBackStack(null)
    }
}

fun AppCompatActivity.replaceFragment(fragment: Fragment) {
    supportFragmentManager.inTransaction { replace(android.R.id.content, fragment).addToBackStack(null) }
    if (fragment !is RandomFragment) {
        Log.e("Replace Frag", "not RandomFrag")
//        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

}

fun AppCompatActivity.removeFragment(fragment: Fragment) {
    supportFragmentManager.inTransaction { remove(fragment) }
}
