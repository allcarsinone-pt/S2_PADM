
package com.allcarsinone.allcarsinone.adapters

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.allcarsinone.allcarsinone.R
import com.allcarsinone.allcarsinone.activities.ViewVehicleActivity
import com.allcarsinone.allcarsinone.databinding.ListviewLayoutVehiclesBinding
import com.allcarsinone.allcarsinone.models.Vehicle
import com.bumptech.glide.Glide

class ListviewVehiclesAdapter(
    private val list: ArrayList<Vehicle>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<ListviewVehiclesAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(vehicle: Vehicle)
    }

    inner class ViewHolder(val viewBinding: ListviewLayoutVehiclesBinding) : RecyclerView.ViewHolder(viewBinding.root) {
        fun bindData(vehicle: Vehicle) {
            viewBinding.listviewVehiclesCarBrandTV.text = "${vehicle.brandname} - ${vehicle.model}"
            viewBinding.listviewVehiclesCarPriceTV.text = vehicle.price.toString()
            viewBinding.listviewVehiclesStandNameTV.text = "Stand ACIO"   // TODO: Ir buscar o nome
            viewBinding.listviewVehiclesLocationTV.text = vehicle.location            // TODO: Ir buscar a localização
            if(vehicle.photos.size > 0) {
                val thumbnail = vehicle.photos[0].url.replace("src/static", "")
                Glide.with(viewBinding.root).load("http://5.180.182.3:8080"+thumbnail).into(viewBinding.listviewVehiclesImageView)
            }

            itemView.setOnClickListener {
                listener.onItemClick(vehicle)
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = ListviewLayoutVehiclesBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bindData(list[position])

        val vehicle = list[position]
        val backStyle = when (position % 2) {
            0 -> R.drawable.rectangle_round_corners_20_back
            1 -> R.drawable.rectangle_round_corners_20_white
            else -> R.color.gray
        }
    }

    override fun getItemCount() = list.size
}
