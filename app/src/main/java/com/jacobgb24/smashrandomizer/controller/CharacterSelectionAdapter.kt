package com.jacobgb24.smashrandomizer.controller

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import com.jacobgb24.smashrandomizer.model.Character
import com.jacobgb24.smashrandomizer.R
import com.jacobgb24.smashrandomizer.model.mainCharacterList
import kotlinx.android.synthetic.main.item_character_selection.view.*

class CharacterSelectionAdapter(private val context: Context): BaseAdapter(),
    CharacterClickHandler {

    override fun getCount(): Int {
        return mainCharacterList.size
    }

    override fun getItem(position: Int): Any {
        return mainCharacterList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    class CharacterViewHolder(val v: View) {
        var image: ImageView = v.image_character_icon

        fun bind(pos: Int, character: Character, handler: CharacterClickHandler) {
            with(image) {
                contentDescription = character.name
                if (Build.VERSION.SDK_INT >= 26) {
                    tooltipText = "Toggle ${character.name}"
                }
                isSelected = character.isSelected
                setOnClickListener {
                    handler.onClick(pos)
                }
                setImageDrawable(character.iconDrawable)
            }
        }
    }

    override fun onClick(pos: Int) {
        mainCharacterList[pos].toggle()
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
        viewHolder.bind(position, mainCharacterList[position], this)
        return viewHolder.v
    }


}

interface CharacterClickHandler {
    fun onClick(pos: Int)
}