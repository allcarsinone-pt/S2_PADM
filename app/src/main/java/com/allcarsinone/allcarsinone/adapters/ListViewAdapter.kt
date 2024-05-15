package com.allcarsinone.allcarsinone.adapters

import android.graphics.Typeface
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.allcarsinone.allcarsinone.Notifications
import com.allcarsinone.allcarsinone.R
import java.text.SimpleDateFormat
import java.util.Date

class ListViewAdapter(private val list: ArrayList<Notifications>) : RecyclerView.Adapter<ListViewAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
    {
        val iv: ImageView = view.findViewById<ImageView>(R.id.Notifications_Image_LV)
        val bv: TextView = view.findViewById<TextView>(R.id.Notifications_Brand_LV)
        val cv: TextView = view.findViewById<TextView>(R.id.Notifications_Comment_LV)
        val dv: TextView = view.findViewById<TextView>(R.id.Notifications_Date_LV)
        val layout: LinearLayout = view.findViewById<LinearLayout>(R.id.Notifications_Inner_LAY)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder
    {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.listview_layout, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int)
    {
        val notify = list[position]
        val colors = when(position % 2) {
            0 -> R.color.generalBack
            1 -> R.color.white
            else -> R.color.gray
        }
        //viewHolder.layout.setBackgroundResource(colors)

        viewHolder.iv.setImageResource(notify.image);
        viewHolder.bv.text = notify.brand
        viewHolder.cv.text = notify.comment

        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val fdate = sdf.format(notify.date)
        viewHolder.dv.text = fdate
    }
    override fun getItemCount() = list.size
}
