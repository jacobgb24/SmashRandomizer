package com.jacobgb24.smashrandomizer.controller

import android.content.Context
import android.graphics.Color
import androidx.core.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import com.jacobgb24.smashrandomizer.*
import com.jacobgb24.smashrandomizer.model.Character
import kotlinx.android.synthetic.main.item_pool_character.view.*


class IronmanResultsAdapter(
    private val context: Context,
    private var characterList: ArrayList<Character>,
    private val finalPos: Int
)
    : BaseAdapter() {

    override fun getCount(): Int {
        return characterList.size
    }

    override fun getItem(position: Int): Any {
        return characterList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    class ResultsViewHolder(val v: View) {
        private var image: ImageView = v.image_character_icon

        fun bind(character: Character, state: ResultState) {
            with(image) {
                setHelp(character.name)
                setImageDrawable(character.iconDrawable)
                when(state) {
                    ResultState.WON -> setBackgroundColor(getColor(v.context, R.color.green, 60))
                    ResultState.LOST -> setBackgroundColor(getColor(v.context, R.color.red, 120))
                    else -> {
                        setSaturation(10)
                        setBackgroundColor(ContextCompat.getColor(v.context, android.R.color.transparent))
                    }
                }
            }
        }
    }

    enum class ResultState { WON, LOST, NOT_SEEN}

    private fun getState(charPos: Int, finalPos: Int): ResultState {
        return when {
            charPos < finalPos -> ResultState.WON
            charPos == finalPos -> ResultState.LOST
            else -> ResultState.NOT_SEEN
        }
    }


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val viewHolder: ResultsViewHolder?
        if (convertView == null) {
            val charView = LayoutInflater.from(context).inflate(R.layout.item_pool_character, parent, false)
            viewHolder = ResultsViewHolder(charView)
            charView.tag = viewHolder
        }
        else {
            viewHolder = convertView.tag as ResultsViewHolder
        }
        viewHolder.bind(characterList[position], getState(position, finalPos))
        return viewHolder.v
    }
}


