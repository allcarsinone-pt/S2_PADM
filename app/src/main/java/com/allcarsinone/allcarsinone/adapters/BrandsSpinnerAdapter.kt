package com.allcarsinone.allcarsinone.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.allcarsinone.allcarsinone.models.Brand

class BrandsSpinnerAdapter(context: Context, val mList: MutableList<Brand>): ArrayAdapter<Brand>(context,android.R.layout.simple_spinner_item) {
    override fun getCount(): Int {
        return mList.size
    }

    override fun getItem(position: Int): Brand? {
        return mList[position]
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view: View? = null
        if(convertView != null){
            return convertView
        }
        else {
            view = LayoutInflater.from(context).inflate(android.R.layout.simple_spinner_item, parent, false)
            val tv = view.findViewById<TextView>(android.R.id.text1)
            tv.text = getItem(position)!!.name
            return view
        }
    }
}