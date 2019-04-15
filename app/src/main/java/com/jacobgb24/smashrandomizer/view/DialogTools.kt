package com.jacobgb24.smashrandomizer.view

import android.app.AlertDialog
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.RippleDrawable
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import com.jacobgb24.smashrandomizer.R
import com.jacobgb24.smashrandomizer.asDP
import com.jacobgb24.smashrandomizer.getColor

/**
 * Defines a set of tools for hand-defining a dialog.
 * These methods should only be called on a view being used for a dialog.
 * The root view of the dialog must be a LinearLayout with id `dialog_root`
 *
 * Generally the below methods should be called in order, but title or message may be skipped
 */


/**
 * Creates a dialog with the given view and adds a border to it
 */
fun createDialog(context: Context, view: View): AlertDialog {
    val builder = AlertDialog.Builder(context)
    val border = GradientDrawable()
    border.setColor(Color.WHITE)
    border.setStroke(2.asDP(), Color.DKGRAY)
    view.background = border
    builder.setView(view)
    return builder.create()
}


/**
 * Adds a title to the dialog.
 */
fun View.setTitle(titleText: String) {
    val title = TextView(context)
    with(title) {
        setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
        text = titleText
        setTextColor(Color.BLACK)
        textAlignment = TextView.TEXT_ALIGNMENT_CENTER
        typeface = ResourcesCompat.getFont(context, R.font.css_font_light)

        val marginParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        marginParams.setMargins(24.asDP(), 32.asDP(), 24.asDP(), 0)
        layoutParams = marginParams
    }

    try {
        findViewById<LinearLayout>(R.id.dialog_root).addView(title, 0)
    } catch (e: Exception) {
        Log.e("DIALOG", "Couldn't add title")
    }
}

/**
 * Adds a message to the dialog.
 * It will be placed in the second position
 */
fun View.setMessage(messageText: String) {
    val message = TextView(context)
    with(message) {
        setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
        text = messageText
        setTextColor(Color.DKGRAY)
        textAlignment = TextView.TEXT_ALIGNMENT_CENTER
        typeface = ResourcesCompat.getFont(context, R.font.css_font_light)

        val marginParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        marginParams.setMargins(24.asDP(), 16.asDP(), 24.asDP(), 16.asDP())
        layoutParams = marginParams
    }

    try {
        findViewById<LinearLayout>(R.id.dialog_root).addView(message, 1)
    } catch (e: Exception) {
        Log.e("DIALOG", "Couldn't add title")
    }
}

/**
 * Adds a negative and positive button to the dialog.
 */
fun View.addButtons(negText: String, posText: String): Pair<Button, Button> {

    fun makeButton(context: Context, buttText: String, isPositive: Boolean): Button {
        val button = Button(context)
        with(button) {
            text = buttText
            isAllCaps = false
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
            setTextColor(Color.WHITE)
            typeface = ResourcesCompat.getFont(context, R.font.css_font)

            backgroundTintList = if (isPositive) ColorStateList.valueOf(getColor(context, R.color.orange))
            else ColorStateList.valueOf(getColor(context, R.color.darkGray))

            val ripple = RippleDrawable(ColorStateList.valueOf(
                getColor(context, R.color.lightGray)), background, background)
            ripple.paddingMode = LayerDrawable.PADDING_MODE_STACK  // prevents it from adding padding
            background = ripple
        }
        return button

    }
    // Define relative layout that holds buttons
    val buttonContainer = LinearLayout(context)
    val params = LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
    buttonContainer.layoutParams = params
    params.setMargins(8.asDP(), 16.asDP(), 8.asDP(), 8.asDP())
    buttonContainer.weightSum = 2f

    // Define buttons and add to view
    val negButton = makeButton(context, negText, false)
    val posButton = makeButton(context, posText, true)
    negButton.id = View.generateViewId()
    posButton.id = View.generateViewId()
    buttonContainer.addView(negButton)
    buttonContainer.addView(posButton)


    // Make Positive button aligned with parent end
    val posParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
    posParams.setMargins(8.asDP(), 0, 8.asDP(), 0)
    posButton.layoutParams = posParams

    // Make Negative button align to start of positive button
    val negParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
    negParams.setMargins(8.asDP(), 0, 8.asDP(), 0)

    negButton.layoutParams = negParams

    try {
        findViewById<LinearLayout>(R.id.dialog_root).addView(buttonContainer)
    } catch (e: Exception) {
        Log.e("DIALOG", "Couldn't add buttons")
    }

    return Pair(negButton, posButton)

}

