package com.chronometron.test.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.chronometron.test.R


class IconTextAdapter(
    context: Context,
    private val icons: List<Int>,
    private val texts: List<String>
) : ArrayAdapter<String>(context, R.layout.spinner_item, texts) {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: inflater.inflate(R.layout.spinner_item, parent, false)
        val iconView = view.findViewById<ImageView>(R.id.icon)
        val textView = view.findViewById<TextView>(R.id.text)

        iconView.setImageResource(icons[position])
        textView.text = texts[position]

        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getView(position, convertView, parent)
    }
}
