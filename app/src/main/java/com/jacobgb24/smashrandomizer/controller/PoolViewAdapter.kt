package com.jacobgb24.smashrandomizer.controller

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import com.jacobgb24.smashrandomizer.R
import com.jacobgb24.smashrandomizer.model.Character
import com.jacobgb24.smashrandomizer.model.getSelected
import com.jacobgb24.smashrandomizer.model.mainCharacterList
import com.jacobgb24.smashrandomizer.setHelp
import kotlinx.android.synthetic.main.item_pool_character.view.*

class PoolViewAdapter(private val context: Context): BaseAdapter() {

    private val characterList = mainCharacterList.getSelected()


    override fun getCount(): Int {
        return characterList.size
    }

    override fun getItem(position: Int): Any {
        return characterList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    class CharacterViewHolder(val v: View) {
        var image: ImageView = v.image_character_icon

        fun bind(character: Character) {
            with(image) {
                setHelp(character.name)
                setImageDrawable(character.iconDrawable)
            }
        }
    }


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val viewHolder: CharacterViewHolder?
        if (convertView == null) {
            val charView = LayoutInflater.from(context).inflate(R.layout.item_pool_character, parent, false)
            viewHolder =
                CharacterViewHolder(charView)
            charView.tag = viewHolder
        }
        else {
            viewHolder = convertView.tag as CharacterViewHolder
        }
        viewHolder.bind(characterList[position])
        return viewHolder.v
    }


}