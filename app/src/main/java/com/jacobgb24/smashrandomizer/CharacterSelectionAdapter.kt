package com.jacobgb24.smashrandomizer

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import kotlinx.android.synthetic.main.item_character_selection.view.*

class CharacterSelectionAdapter: BaseAdapter {
    var characterList = ArrayList<Character>()
    var context: Context? = null

    constructor(context: Context, list: ArrayList<Character>) {
        this.context = context
        this.characterList = list
    }

    override fun getCount(): Int {
        return characterList.size
    }

    override fun getItem(position: Int): Any {
        return characterList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        Log.e("Loading view", characterList[position].name)
        var inflater = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        var charView = inflater.inflate(R.layout.item_character_selection, null)
        var image: Drawable = Drawable.createFromStream(
            context!!.assets.open("icons/" + characterList[position].imageName), null)


        charView.image_character_icon.setImageDrawable(image)
        return charView
    }
}