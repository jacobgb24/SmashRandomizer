package com.jacobgb24.smashrandomizer.view

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.util.Log
import android.view.*
import android.widget.Toast
import com.jacobgb24.smashrandomizer.controller.CharacterSelectionAdapter
import com.jacobgb24.smashrandomizer.R
import com.jacobgb24.smashrandomizer.addRippleFG
import com.jacobgb24.smashrandomizer.controller.FragOnBackPressed
import com.jacobgb24.smashrandomizer.controller.MainActivity
import com.jacobgb24.smashrandomizer.model.activePool
import com.jacobgb24.smashrandomizer.model.deletePool
import com.jacobgb24.smashrandomizer.model.savePools
import kotlinx.android.synthetic.main.fragment_character_selection.view.*


class CharacterSelectionFragment : Fragment(), FragOnBackPressed {

    private val fragTag = "CharacterSelectionFrag"
    private lateinit var adapter: CharacterSelectionAdapter
    private var activePoolCopy = activePool.copy()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }

    // Create view hierarchy controlled by fragment.
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_character_selection, container, false)
        adapter = CharacterSelectionAdapter(view.context)
        view.grid_character_selection.adapter = adapter
        (activity as MainActivity).supportActionBar!!.title = "Edit Pool"

        view.button_pool_save.addRippleFG()
        view.button_pool_save.setOnClickListener {
            attemptSave()
        }

        view.button_selection_banner.setOnClickListener {
            view.banner_selection_tooltip.visibility = View.GONE
        }

        return view

    }


    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater!!.inflate(R.menu.selection_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun showSaveAlert() {
        val builder = AlertDialog.Builder(activity)
        with(builder) {
            setTitle("Cancel Pool Edits?")
            setMessage("Leaving without saving will remove the changes you have just made.")
            setPositiveButton("Leave") {dialog, _ ->
                if (activePoolCopy.size() == 0) { // this means a new pool since otherwise activePool.size > 1
                    deletePool(activePool)
                }
                else {
                    activePool = activePoolCopy.copy()
                }
                (activity as MainActivity).removeFragment()
                dialog.dismiss()
            }
            setNegativeButton("Keep Editing") {dialog, _ ->
                dialog.dismiss()
            }
        }
        val dialog = builder.create()
        dialog.show()
    }

    private fun attemptSave() {
        if (activePool.getSelected().size < 2) {
            Toast.makeText(activity, "A pool must have at least two selections", Toast.LENGTH_SHORT).show()
        }
        else {
            Toast.makeText(activity, "Pool Saved", Toast.LENGTH_SHORT).show()
            savePools(activity!!)
            (activity as MainActivity).removeFragment()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.menu_select_all -> {
                activePool.setAllSelection(true)
                adapter.notifyDataSetChanged()
                true
            }
            R.id.menu_deselect_all -> {
                activePool.setAllSelection(false)
                adapter.notifyDataSetChanged()
                true
            }
           // up button handled by main which calls onBackPressed below
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed(): Boolean {
        Log.e(fragTag, "onBack Called")
        showSaveAlert()
        return true
    }

}
