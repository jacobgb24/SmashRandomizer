package com.jacobgb24.smashrandomizer.view

import android.app.AlertDialog
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.jacobgb24.smashrandomizer.R
import com.jacobgb24.smashrandomizer.controller.MainActivity
import com.jacobgb24.smashrandomizer.controller.PoolAdapter
import com.jacobgb24.smashrandomizer.model.Pool
import com.jacobgb24.smashrandomizer.model.activePool
import com.jacobgb24.smashrandomizer.model.newPool
import com.jacobgb24.smashrandomizer.model.pools
import com.leinardi.android.speeddial.SpeedDialActionItem
import kotlinx.android.synthetic.main.dialog_new_pool.view.*
import kotlinx.android.synthetic.main.fragment_pool_list.view.*

/*
TODO: define layout for this fragment
TODO: write list adapter for current pool
TODO: figure out how to transition from this fragment to the character select fragment.
*/
class PoolListFragment : Fragment() {


    private lateinit var adapter: PoolAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    // Create view hierarchy controlled by fragment.
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_pool_list, container, false)
        adapter = PoolAdapter(view.context)
        view.list_pool.adapter = adapter
        (activity as MainActivity).supportActionBar!!.title = "Pools"

//
//        view.button_change_pool.setOnClickListener {
//            newPoolDialog()
//        }
        setUpFAB(view)

//        view.button_pool_edit.setOnClickListener {
//            (activity as MainActivity).addFragment(CharacterSelectionFragment())
//        }

        return view

    }

    fun newPoolDialog() {
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

    fun setUpFAB(view: View) {
        val fab = view.fab_pool
        fab.addActionItem(SpeedDialActionItem.Builder(R.id.fab_rename, R.drawable.ic_butt_ironman_down).create())

        fab.setOnActionSelectedListener {
            when(id) {
                R.id.fab_rename -> {
                    Toast.makeText(activity, "CLICKED RENAME", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }
    }

}
