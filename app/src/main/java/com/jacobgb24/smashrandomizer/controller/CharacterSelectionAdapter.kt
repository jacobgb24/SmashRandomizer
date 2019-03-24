package com.jacobgb24.smashrandomizer.controller

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import com.jacobgb24.smashrandomizer.model.Character
import com.jacobgb24.smashrandomizer.R
import com.jacobgb24.smashrandomizer.model.activePool
import com.jacobgb24.smashrandomizer.setHelp
import kotlinx.android.synthetic.main.item_character_selection.view.*

class CharacterSelectionAdapter(private val context: Context): BaseAdapter(), CharacterClickHandler {

    override fun getCount(): Int {
        return activePool.size()
    }

    override fun getItem(position: Int): Any {
        return activePool[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    class CharacterViewHolder(val v: View) {
        var image: ImageView = v.image_character_icon_selectable

        fun bind(pos: Int, character: Character, handler: CharacterClickHandler) {
            with(image) {
                val hasChar = activePool.contains(character)
                setHelp("${character.name} is ${if (hasChar) "" else "not"} selected")
                isSelected = hasChar
                setOnClickListener {
                    handler.onClick(pos)
                }
                setImageDrawable(character.iconDrawable)
            }
        }
    }

    override fun onClick(pos: Int) {
        activePool.toggle(pos)
        notifyDataSetChanged()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val viewHolder: CharacterViewHolder?
        if (convertView == null) {
            val charView = LayoutInflater.from(context).inflate(R.layout.item_character_selection, parent, false)
            viewHolder =
                CharacterViewHolder(charView)
            charView.tag = viewHolder
        }
        else {
            viewHolder = convertView.tag as CharacterViewHolder
        }
        viewHolder.bind(position, activePool[position], this)
        return viewHolder.v
    }


}

interface CharacterClickHandler {
    fun onClick(pos: Int)
}