package com.ashwin.mvvmrxandroid.view.main

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ashwin.mvvmrxandroid.R
import com.ashwin.mvvmrxandroid.domain.model.Color
import com.ashwin.mvvmrxandroid.view.Constant

class ColorListAdapter : RecyclerView.Adapter<ColorListAdapter.ColorViewHolder>() {
    class ColorViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val nameTextView = view.findViewById<TextView>(R.id.name_text_view)
        private val hexValueTextView = view.findViewById<TextView>(R.id.hex_value_text_view)

        fun bind(color: Color) {
            Log.d(Constant.DEBUG_TAG, "ColorListAdapter.ColorViewHolder: bind( $color )")
            nameTextView.text = color.colorName
            hexValueTextView.text = color.hexValue
        }
    }

    var list = listOf<Color>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_color, parent, false)
        return ColorViewHolder(view)
    }

    override fun onBindViewHolder(holder: ColorViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }
}
