package com.allcarsinone.allcarsinone.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.allcarsinone.allcarsinone.Notifications
import com.allcarsinone.allcarsinone.R
import java.text.SimpleDateFormat
import java.util.Date

sealed class ListItem {
    data class Layout1(val image: Int, val brand: String, val comment: String, val regDate: Date) : ListItem()
    data class Layout2(val image: Int, val brand: String, val regDate: Date) : ListItem()
}
class ListViewAdapter( private val items: List<ListItem>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    inner class LayoutHolder1(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: ListItem.Layout1) {
            // Bind data to views
            val iv: ImageView = itemView.findViewById<ImageView>(R.id.Notifications_Image_LV)
            val bv: TextView = itemView.findViewById<TextView>(R.id.Notifications_Brand_LV)
            bv.text = item.brand
            val cv: TextView = itemView.findViewById<TextView>(R.id.Notifications_Comment_LV)
            cv.text = item.comment
            val dv: TextView = itemView.findViewById<TextView>(R.id.Notifications_Date_LV)
            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val fdt = sdf.format(item.regDate)
            dv.text = fdt
        }
    }
    inner class LayoutHolder2(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: ListItem.Layout2) {
            val iv: ImageView = itemView.findViewById<ImageView>(R.id.Notifications2_Image_LV)
            val bv: TextView = itemView.findViewById<TextView>(R.id.Notifications2_Brand_LV)
            bv.text = item.brand
            val dv: TextView = itemView.findViewById<TextView>(R.id.Notifications2_Date_LV)
            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val fdt = sdf.format(item.regDate)
            dv.text = fdt
        }
    }
    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is ListItem.Layout1 -> 0
            is ListItem.Layout2 -> 1
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            0 -> LayoutHolder1(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.listview_layout, parent, false)
            )
            1 -> LayoutHolder2(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.listview_layout_comments, parent, false)
            )
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = items[position]) {
            is ListItem.Layout1 -> (holder as LayoutHolder1).bind(item)
            is ListItem.Layout2 -> (holder as LayoutHolder2).bind(item)
        }
        val backStyle = when(position % 2) {
            0 -> R.drawable.rectangle_round_corners_20_back
            1 -> R.drawable.rectangle_round_corners_20_white
            else -> R.color.gray
        }
        holder.itemView.setBackgroundResource(backStyle)
    }
    override fun getItemCount() = items.size
}