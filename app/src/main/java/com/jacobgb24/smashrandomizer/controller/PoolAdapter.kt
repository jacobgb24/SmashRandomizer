package com.jacobgb24.smashrandomizer.controller

import android.content.Context
import android.support.v7.widget.CardView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.jacobgb24.smashrandomizer.R
import com.jacobgb24.smashrandomizer.model.Pool
import com.jacobgb24.smashrandomizer.model.activePool
import com.jacobgb24.smashrandomizer.model.pools
import com.jacobgb24.smashrandomizer.model.selectPool
import kotlinx.android.synthetic.main.item_pool.view.*



class PoolAdapter(private val context: Context): BaseAdapter(), PoolClickHandler {


    override fun getCount(): Int {
        return pools.size
    }

    override fun getItem(position: Int): Any {
        return pools[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    class PoolViewHolder(val v: View) {
        val name: TextView = v.text_list_pool_name
        val size: TextView = v.text_list_pool_size
        val charList: WrappingGridView = v.grid_pool_chars
        val card: CardView = v.card_pool

        fun bind(pool: Pool, handler: PoolClickHandler, pos: Int) {
            name.text = pool.name
            size.text = "Size: ${pool.size()}"
            charList.adapter = CharactersAdapter(v.context, pool.getSelected())
            if (pool != activePool) {
                charList.visibility = View.GONE
            }
            else {
                charList.visibility = View.VISIBLE
            }
            card.setOnClickListener {
                handler.onClick(pos)

            }
        }

    }

    override fun onClick(pos: Int) {
        selectPool(pos)
        notifyDataSetChanged()
    }


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val viewHolder: PoolViewHolder?
        if (convertView == null) {
            val charView = LayoutInflater.from(context).inflate(R.layout.item_pool, parent, false)
            viewHolder =
                PoolViewHolder(charView)
            charView.tag = viewHolder
        }
        else {
            viewHolder = convertView.tag as PoolViewHolder
        }
        viewHolder.bind(pools[position], this, position)
        return viewHolder.v
    }


}


interface PoolClickHandler {
    fun onClick(pos: Int)
}