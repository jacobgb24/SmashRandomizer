package com.jacobgb24.smashrandomizer.view

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.jacobgb24.smashrandomizer.R
import com.jacobgb24.smashrandomizer.addRippleFG
import com.jacobgb24.smashrandomizer.controller.CharacterSelectionAdapter
import com.jacobgb24.smashrandomizer.controller.FragOnBackPressed
import com.jacobgb24.smashrandomizer.controller.MainActivity
import com.jacobgb24.smashrandomizer.model.*
import kotlinx.android.synthetic.main.fragment_character_selection.view.*


class CharacterSelectionFragment : Fragment(), FragOnBackPressed {

    private val fragTag = "CharacterSelectionFrag"
    private lateinit var adapter: CharacterSelectionAdapter
    private lateinit var activePoolBackup: Pool
    private var activePoolPos: Int = getActiveIndex()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }

    // Create view hierarchy controlled by fragment.
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_character_selection, container, false)
        adapter = CharacterSelectionAdapter(view.context)
        view.grid_character_selection.adapter = adapter
        (activity as MainActivity).supportActionBar!!.title = getString(R.string.edit_pool_title)

        activePoolBackup = activePool.copy()

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

    private fun attemptSave() {
        if (activePool.getSelected().size < 2) {
            Toast.makeText(activity, getString(R.string.pool_size_req), Toast.LENGTH_SHORT).show()
        }
        else {
            Toast.makeText(activity, getString(R.string.pool_saved), Toast.LENGTH_SHORT).show()
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
        showLeaveEditDialog()
        return true
    }

    private fun showLeaveEditDialog() {
        val view = requireActivity().layoutInflater.inflate(R.layout.dialog_basic, null)
        view.setTitle(getString(R.string.cancel_pool_edits))
        view.setMessage(getString(R.string.cancel_pool_edits_message))
        val (negButton, posButton) = view.addButtons(getString(R.string.keep_editing), getString(R.string.leave))

        val dialog = createDialog(context!!, view)

        negButton.setOnClickListener {
            dialog.dismiss()
        }
        posButton.setOnClickListener {
            if (activePoolBackup.size() == 0) { // this means a new pool since otherwise activePool.size > 1
                deletePool(activePool)
            }
            else {
                pools[activePoolPos] = activePoolBackup.copy()
                selectPool(activePoolPos)
            }
            (activity as MainActivity).removeFragment()
            dialog.dismiss()
        }

        dialog.show()
    }

}
