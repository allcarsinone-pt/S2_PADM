
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
import com.allcarsinone.allcarsinone.models.Vehicle
import java.text.SimpleDateFormat

class ListviewVehiclesAdapter(private val list: ArrayList<Vehicle>) : RecyclerView.Adapter<ListviewVehiclesAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
    {
        val image: ImageView = view.findViewById<ImageView>(R.id.listviewVehiclesImageView)
        val brand: TextView = view.findViewById<TextView>(R.id.listviewVehiclesCarBrand_TV)
        val price: TextView = view.findViewById<TextView>(R.id.listviewVehiclesCarPrice_TV)
        val stand: TextView = view.findViewById<TextView>(R.id.listviewVehiclesStandName_TV)
        val locat: TextView = view.findViewById<TextView>(R.id.listviewVehiclesLocation_TV)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder
    {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.listview_layout_vehicles, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int)
    {
        val vehicle = list[position]
        val backStyle = when(position % 2) {
            0 -> R.drawable.rectangle_round_corners_20_back
            1 -> R.drawable.rectangle_round_corners_20_white
            else -> R.color.gray
        }

        //viewHolder.image.setImageURI(vehicle.photos[0])
        viewHolder.brand.text = vehicle.brandname + " - " + vehicle.model
        viewHolder.price.text = vehicle.price.toString()
        viewHolder.stand.text = "Ir buscar o nome" // vehicle.st standid.toString() // TODO: Ir buscar o nome
        viewHolder.locat.text = "Barcelos"
    }
    override fun getItemCount() = list.size
}