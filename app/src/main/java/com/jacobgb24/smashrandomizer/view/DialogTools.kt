package com.jacobgb24.smashrandomizer.view

import android.app.AlertDialog
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.RippleDrawable
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.jacobgb24.smashrandomizer.R
import com.jacobgb24.smashrandomizer.controller.MainActivity
import com.jacobgb24.smashrandomizer.getColor
import com.jacobgb24.smashrandomizer.asDP
import com.jacobgb24.smashrandomizer.model.*
import kotlinx.android.synthetic.main.dialog_new_pool.view.*
import kotlinx.android.synthetic.main.dialog_rename_pool.view.*

import java.lang.Exception

/**
 * Defines a set of tools for hand-defining a dialog.
 * These methods should only be called on a view being used for a dialog.
 * The root view of the dialog must be a LinearLayout with id `dialog_root`
 *
 * Generally the below methods should be called in order, but title or message may be skipped
 */


/**
 * Creates a dialog with the given view
 */
fun createDialog(context: Context, view: View): AlertDialog {
    val builder = AlertDialog.Builder(context)
    builder.setView(view)
    return builder.create()
}


/**
 * Adds a title to the dialog.
 */
fun View.setTitle(titleText: String) {
    val title = TextView(context)
    with(title) {
        setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f)
        text = titleText
        setTextColor(Color.BLACK)

        val marginParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT)
        marginParams.setMargins(24.asDP(), 24.asDP(), 24.asDP(), 0)
        layoutParams = marginParams
    }

    try {
        findViewById<LinearLayout>(R.id.dialog_root).addView(title, 0)
    } catch (e: Exception) {
        Log.e("DIALOG", "Could'nt add title")
    }
}

/**
 * Adds a message to the dialog.
 * It will be placed in the second position
 */
fun View.setMessage(messageText: String) {
    val message = TextView(context)
    with(message) {
        setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
        text = messageText
        setTextColor(Color.DKGRAY)

        val marginParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT)
        marginParams.setMargins(24.asDP(), 8.asDP(), 24.asDP(), 0)
        layoutParams = marginParams
    }

    try {
        findViewById<LinearLayout>(R.id.dialog_root).addView(message, 1)
    } catch (e: Exception) {
        Log.e("DIALOG", "Could'nt add title")
    }
}

/**
 * Adds a negative and positive button to the dialog.
 */
fun View.addButtons(negText: String, posText: String): Pair<Button, Button> {

    fun makeButton(context: Context, buttText: String): Button {
        val button = Button(context)
        with(button) {
            text = buttText.toUpperCase()
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
            setTextColor(getColor(context, R.color.colorAccent))
            background = RippleDrawable(ColorStateList.valueOf(
                Color.parseColor("#DEDEDE")), null, background)
            setPadding(8.asDP(), 8.asDP(), 8.asDP(), 8.asDP())
        }
        return button

    }
    // Define relative layout that holds buttons
    val relLayout = RelativeLayout(context)
    val params = RelativeLayout.LayoutParams(
        RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.MATCH_PARENT)
    relLayout.layoutParams = params
    params.setMargins(8.asDP(), 8.asDP(), 8.asDP(), 8.asDP())

    // Define buttons and add to view
    val negButton = makeButton(context, negText)
    val posButton = makeButton(context, posText)
    negButton.id = View.generateViewId()
    posButton.id = View.generateViewId()
    relLayout.addView(posButton)
    relLayout.addView(negButton)

    // Make Positive button aligned with parent end
    val posParams = RelativeLayout.LayoutParams(
        RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
    posParams.addRule(RelativeLayout.ALIGN_PARENT_END)
    posButton.layoutParams = posParams

    // Make Negative button align to start of positive button
    val negParams = RelativeLayout.LayoutParams(
        RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
    negParams.addRule(RelativeLayout.START_OF, posButton.id)
    negButton.layoutParams = negParams

    try {
        findViewById<LinearLayout>(R.id.dialog_root).addView(relLayout)
    } catch (e: Exception) {
        Log.e("DIALOG", "Couldn't add buttons")
    }

    return Pair(negButton, posButton)

}


