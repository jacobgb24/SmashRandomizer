package com.jacobgb24.smashrandomizer.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import com.jacobgb24.smashrandomizer.controller.CharacterSelectionAdapter
import com.jacobgb24.smashrandomizer.R
import com.jacobgb24.smashrandomizer.controller.MainActivity
import com.jacobgb24.smashrandomizer.model.mainCharacterList
import com.jacobgb24.smashrandomizer.model.setAllSelection
import kotlinx.android.synthetic.main.fragment_character_selection.view.*


class CharacterSelectionFragment : Fragment() {

    private lateinit var adapter: CharacterSelectionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    // Create view hierarchy controlled by fragment.
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_character_selection, container, false)
        adapter = CharacterSelectionAdapter(view.context)
        view.grid_character_selection.adapter = adapter

        return view

    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater!!.inflate(R.menu.selection_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.menu_select_all -> {
                mainCharacterList.setAllSelection(true)
                adapter.notifyDataSetChanged()
                true
            }
            R.id.menu_deselect_all -> {
                mainCharacterList.setAllSelection(false)
                adapter.notifyDataSetChanged()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}
