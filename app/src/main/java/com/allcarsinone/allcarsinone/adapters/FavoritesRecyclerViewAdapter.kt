package com.allcarsinone.allcarsinone.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.allcarsinone.allcarsinone.databinding.FavoritesLayoutBinding
import com.allcarsinone.allcarsinone.models.Favorite
import com.allcarsinone.allcarsinone.models.FavoriteUserCar
import com.bumptech.glide.Glide

class FavoritesRecyclerViewAdapter(var mList: MutableList<FavoriteUserCar>): RecyclerView.Adapter<FavoritesRecyclerViewAdapter.Viewholder>() {

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        val viewBinding = FavoritesLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Viewholder(viewBinding)
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        holder.bindData(mList[position])
    }
    inner class Viewholder(val viewBinding: FavoritesLayoutBinding): RecyclerView.ViewHolder(viewBinding.root) {
        fun bindData(favorite: FavoriteUserCar) {
            viewBinding.favoritesLayoutPriceTv.text = favorite.price.toString() + "€"
            viewBinding.favoritesLayoutVehicleNameTv.text = favorite.carname
            Glide.with(viewBinding.root).load("https://c4ab-87-196-81-55.ngrok-free.app"+favorite.thumbnail).into(viewBinding.favorites1Iv)

        }
    }
}