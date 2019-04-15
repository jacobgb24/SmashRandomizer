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
 * Creates a dialog with the given view
 */
private fun createDialog(context: Context, view: View): AlertDialog {
    val builder = AlertDialog.Builder(context)
    builder.setView(view)
    return builder.create()
}


/**
 * Adds a title to the given view.
 * The view must have a LinearLayout with id: dialog_root. This should be the root of the view in most cases
 *
 */
private fun View.setTitle(titleText: String) {
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

private fun View.setMessage(messageText: String) {
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
 * Adds a negative and positive button to the view.
 * The view must have a LinearLayout with id: dialog_root. This should be the root of the view in most cases
 *
 */
private fun View.addButtons(negText: String, posText: String): Pair<Button, Button> {

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


/**
 * Shows the delete dialog triggered by the delete icon in PoolListFragment
 */
fun showDeletePoolDialog(frag: PoolListFragment) {
    val view = frag.requireActivity().layoutInflater.inflate(R.layout.dialog_basic, null)

    view.setTitle("Delete ${activePool.name}?")
    val (negButton, posButton) = view.addButtons("Cancel", "Delete")

    val dialog = createDialog(frag.context!!, view)

    negButton.setOnClickListener {
        dialog.dismiss()
    }
    posButton.setOnClickListener {
        deletePool(activePool)
        frag.updateView()
        dialog.dismiss()
    }

    dialog.show()
}

/**
 * Shows the rename dialog triggered by the rename icon in PoolListFragment
 */
fun showRenameDialog(frag: PoolListFragment) {
    val view = frag.requireActivity().layoutInflater.inflate(R.layout.dialog_rename_pool, null)
    view.setTitle("Rename Pool")
    val (negButton, posButton) = view.addButtons("Cancel", "Rename")

    val poolName = view.edittext_rename_pool
    poolName.setText(activePool.name)
    poolName.setSelection(poolName.text.length)
    val dialog = createDialog(frag.context!!, view)

    negButton.setOnClickListener {
        dialog.dismiss()
    }
    posButton.setOnClickListener {
        var name = poolName.text.toString()
        if (name.isEmpty()) {
            name = "Unnamed Pool"
        }
        activePool.name = name
        frag.updateView()
        dialog.dismiss()
    }


    dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
    dialog.show()
}

/**
 * Shows the new pool dialog triggered by the + icon in PoolListFragment
 */
fun showNewPoolDialog(frag: PoolListFragment) {
    val view = frag.requireActivity().layoutInflater.inflate(R.layout.dialog_new_pool, null)
    view.setTitle("New Pool")
    val (negButton, posButton) = view.addButtons("Cancel", "Create")

    val poolName = view.edittext_new_pool
    val checkbox = view.checkbox_new_pool_sel_all

    val dialog = createDialog(frag.context!!, view)

    negButton.setOnClickListener {
        dialog.dismiss()
    }
    posButton.setOnClickListener {
        var name = poolName.text.toString()
        if (name.isEmpty()) {
            name = "Unnamed Pool"
        }
        newPool(name, checkbox.isChecked)
        frag.updateView()
        dialog.dismiss()
        (frag.activity as MainActivity).addFragment(CharacterSelectionFragment())
    }

    dialog.show()
}

/**
 * Shows the leave edit dialog triggered by going back in CharacterSelectionFragment
 */
fun showLeaveEditDialog(frag: CharacterSelectionFragment, activePoolBackup: Pool) {
    val view = frag.requireActivity().layoutInflater.inflate(R.layout.dialog_basic, null)
    view.setTitle("Cancel Pool Edits?")
    view.setMessage("Leaving without saving will remove the changes you have just made.")
    val (negButton, posButton) = view.addButtons("Keep Editing", "Leave")

    val dialog = createDialog(frag.context!!, view)

    negButton.setOnClickListener {
        dialog.dismiss()
    }
    posButton.setOnClickListener {
        if (activePoolBackup.size() == 0) { // this means a new pool since otherwise activePool.size > 1
            deletePool(activePool)
        }
        else {
            activePool = activePoolBackup.copy()
        }
        (frag.activity as MainActivity).removeFragment()
        dialog.dismiss()
    }

    dialog.show()
}

/**
 * Shows the clear pool dialog triggered by the Clear Pool option in Preference Fragment
 */
fun showClearPoolDialog(frag: PreferenceFragment) {
    val view = frag.requireActivity().layoutInflater.inflate(R.layout.dialog_basic, null)
    view.setTitle("Clear all pool data?")
    view.setMessage("This cannot be undone.")
    val (negButton, posButton) = view.addButtons("Cancel", "Clear")

    val dialog = createDialog(frag.context!!, view)
    negButton.setOnClickListener {
        dialog.dismiss()
    }
    posButton.setOnClickListener {
        clearPools(frag.context!!)
        dialog.dismiss()
    }
    dialog.show()
}