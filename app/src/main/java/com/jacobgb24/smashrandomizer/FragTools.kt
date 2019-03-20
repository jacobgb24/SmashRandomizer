package com.jacobgb24.smashrandomizer

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity

// Higher order transaction function. Used by AppCompatActivity extensions below.
inline fun FragmentManager.inTransaction(
    func: FragmentTransaction.() -> FragmentTransaction) {
    beginTransaction().func().commit()
}

// Extend AppCompatActivity to add/remove/replace fragments. Used by Main Activity
fun AppCompatActivity.addFragment(fragment: Fragment, frameId: Int) {
    supportFragmentManager.inTransaction {
        add(frameId, fragment).addToBackStack(null)
    }
}

fun AppCompatActivity.replaceFragment(fragment: Fragment, frameId: Int) {
    supportFragmentManager.inTransaction { replace(frameId, fragment) }
}

fun AppCompatActivity.removeFragment(fragment: Fragment) {
    supportFragmentManager.inTransaction { remove(fragment) }
}
