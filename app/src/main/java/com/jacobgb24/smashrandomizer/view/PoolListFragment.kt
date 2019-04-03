package com.jacobgb24.smashrandomizer.view

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.jacobgb24.smashrandomizer.R
import com.jacobgb24.smashrandomizer.controller.CharactersAdapter
import com.jacobgb24.smashrandomizer.controller.MainActivity
import com.jacobgb24.smashrandomizer.controller.PoolAdapter
import com.jacobgb24.smashrandomizer.model.*
import kotlinx.android.synthetic.main.dialog_new_pool.view.*
import kotlinx.android.synthetic.main.fragment_pool_list.view.*

/*
TODO: define layout for this fragment
TODO: write list adapter for current pool
TODO: figure out how to transition from this fragment to the character select fragment.
*/
class PoolListFragment : Fragment(), PoolClickHandler {


    private lateinit var currentCharsAdapter: CharactersAdapter
    private lateinit var poolAdapter: PoolAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    // Create view hierarchy controlled by fragment.
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_pool_list, container, false)
        currentCharsAdapter = CharactersAdapter(view.context, activePool.getSelected())
        poolAdapter = PoolAdapter(view.context, this)

        view.grid_pool_list_chars.adapter = currentCharsAdapter
        view.list_pool.adapter = poolAdapter
        view.text_pool_current.text = activePool.name
        (activity as MainActivity).supportActionBar!!.title = "Pools"

//
        view.button_change_pool.setOnClickListener {
            newPoolDialog()
        }
        view.button_pool_edit.setOnClickListener {
            (activity as MainActivity).addFragment(CharacterSelectionFragment())
        }
        view.button_pool_delete.setOnClickListener {
            Toast.makeText(view.context, "Not implemented", Toast.LENGTH_SHORT).show()
        }
        view.button_pool_rename.setOnClickListener {
            Toast.makeText(view.context, "Not implemented", Toast.LENGTH_SHORT).show()
        }

        return view

    }

    override fun onPoolClick(pos: Int) {
        selectPool(pos)
        poolAdapter.notifyDataSetChanged()
        view!!.text_pool_current.text = activePool.name
        currentCharsAdapter.characterList = activePool.getSelected()
        currentCharsAdapter.notifyDataSetChanged()
    }

    private fun newPoolDialog() {
        val builder = AlertDialog.Builder(activity)
        val view = requireActivity().layoutInflater.inflate(R.layout.dialog_new_pool, null)
        val poolName = view.edittext_new_pool
        val check = view.checkbox_new_pool_sel_all
        with(builder) {
            setTitle("New Pool")
            setView(view)
            setPositiveButton("Create") {dialog, _ ->
                var name = poolName.text.toString()
                if (name.isEmpty()) {
                    name = "Unnamed Pool"
                }
                newPool(name, check.isChecked)
                dialog.dismiss()
                (activity as MainActivity).addFragment(CharacterSelectionFragment())
            }
            setNegativeButton("Cancel") {dialog, _ ->
                dialog.dismiss()
            }
        }
        val dialog = builder.create()
        dialog.show()
    }

}


interface PoolClickHandler {
    fun onPoolClick(pos: Int)
}
