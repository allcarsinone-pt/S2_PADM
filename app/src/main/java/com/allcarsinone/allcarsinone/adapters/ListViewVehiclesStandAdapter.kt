
package com.allcarsinone.allcarsinone.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.allcarsinone.allcarsinone.R
import com.allcarsinone.allcarsinone.databinding.ActivityViewVehicleStandBinding
import com.allcarsinone.allcarsinone.models.Vehicle
import com.bumptech.glide.Glide

class ListViewVehiclesStandAdapter(
    private val list: ArrayList<Vehicle>,
    private val listener: OnItemStandClickListener
) : RecyclerView.Adapter<ListViewVehiclesStandAdapter.ViewHolder>() {

    interface OnItemStandClickListener {
        fun onItemClick(vehicle: Vehicle)
    }

    inner class ViewHolder(val viewBinding: ActivityViewVehicleStandBinding) : RecyclerView.ViewHolder(viewBinding.root) {
        fun bindData(vehicle: Vehicle) {
            viewBinding.listviewVehiclesStandCarBrandTV.text = "${vehicle.brandname} - ${vehicle.model}"
            viewBinding.listviewVehiclesStandCarPriceTV.text = "€ " + String.format("%.2f", vehicle.price)
            viewBinding.listviewVehiclesStandStandNameTV.text = "Stand Acio"   // TODO: Ir buscar o nome
            if(vehicle.photos.size > 0) {
                val thumbnail = vehicle.photos[0].url.replace("src/static", "")
                Glide.with(viewBinding.root).load("http://5.180.182.3:8080"+thumbnail).into(viewBinding.listviewVehiclesStandImageView)
            }

            itemView.setOnClickListener {
                listener.onItemClick(vehicle)
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = ActivityViewVehicleStandBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
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
