package com.jacobgb24.smashrandomizer

import android.content.Context
import android.graphics.drawable.Drawable
import android.media.Image
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.GridView
import android.widget.ImageView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_character_selection.view.*

class CharacterSelectionAdapter(private val context: Context): BaseAdapter() {
    var characterList = generateCharacters(context)

    override fun getCount(): Int {
        return characterList.size
    }

    override fun getItem(position: Int): Any {
        return characterList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    class CharacterViewHolder(val v: View, var character: String = "") {
        var image: ImageView = v.image_character_icon
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val viewHolder: CharacterViewHolder?
        if (convertView == null) {
            val charView = LayoutInflater.from(context).inflate(R.layout.item_character_selection, parent, false)
            viewHolder = CharacterViewHolder(charView, characterList[position].name)
            charView.tag = viewHolder
        }
        else {
            viewHolder = convertView.tag as CharacterViewHolder
//            Log.e("Reusing viewholder", "for: ${characterList[position].name}\nwas: ${viewHolder.character}")
        }
        viewHolder.character = characterList[position].name
        Glide.with(context).load(characterList[position].getIconUri()).into(viewHolder.image)
        return viewHolder.v
    }


}